package com.example.test_sdk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivo.sdk.core.model.ConversationId
import com.aivo.sdk.runtime.agent.AgentEvent
import com.example.test_sdk.agent.WeatherAgentFactory
import com.example.test_sdk.config.BuildConfig
import com.example.test_sdk.data.network.WeatherRemoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val weatherService: WeatherRemoteService = WeatherRemoteService(apiKey = BuildConfig.WEATHER_API_KEY),
    private val ollamaApiKey: String = BuildConfig.OLLAMA_API_KEY,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val conversationId = ConversationId("weather-agent-session")
    private var messageCounter = 0L

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> sendMessage(intent.text)
            is ChatIntent.UpdateInput -> _state.update { it.copy(inputText = intent.text) }
            is ChatIntent.SelectSuggestion -> sendMessage(intent.prompt)
            is ChatIntent.ClearChat -> _state.update {
                it.copy(
                    messages = emptyList(),
                    currentStatus = null,
                    activeToolName = null,
                    error = null,
                )
            }
            is ChatIntent.DismissError -> _state.update { it.copy(error = null) }
        }
    }

    private fun nextId(prefix: String): String = "$prefix-${++messageCounter}"

    private fun sendMessage(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty() || _state.value.isLoading) return

        val userMessage = ChatMessage(
            id = nextId("user"),
            sender = MessageSender.USER,
            text = trimmed,
            timestamp = "Just now",
        )

        _state.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isLoading = true,
                error = null,
                currentStatus = "Analyzing country query...",
            )
        }

        executeWithAivoSdkOrFallback(trimmed)
    }

    /**
     * Executes the autonomous agent loop via Aivo SDK with Ollama.
     * If Ollama is unavailable/unreachable locally, automatically falls back
     * to direct live tool execution with FreeOpenAPI data.
     */
    private fun executeWithAivoSdkOrFallback(prompt: String) {
        viewModelScope.launch {
            var streamedAny = false
            try {
                val aivo = WeatherAgentFactory.create(
                    apiKey = ollamaApiKey,
                    service = weatherService,
                )

                val agentMsgId = nextId("agent")
                var initialAgentMessageAdded = false
                val responseBuilder = StringBuilder()

                aivo.stream(conversationId, prompt)
                    .catch { throwable ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "Ollama Error: ${throwable.message ?: throwable.toString()}",
                                currentStatus = null,
                                activeToolName = null,
                            )
                        }
                    }
                    .collect { event ->
                        streamedAny = true
                        when (event) {
                            is AgentEvent.ToolCallStarted -> {
                                val friendlyDesc = if (event.toolName.contains("coordinates")) {
                                    "Calling Tool 1: get_country_coordinates..."
                                } else {
                                    "Calling Tool 2: get_weather_conditions..."
                                }
                                _state.update {
                                    it.copy(
                                        currentStatus = friendlyDesc,
                                        activeToolName = event.toolName,
                                    )
                                }
                            }

                            is AgentEvent.ToolCallExecuted -> {
                                val toolMsg = ChatMessage(
                                    id = nextId("tool"),
                                    sender = MessageSender.TOOL_EVENT,
                                    text = "Executed: ${event.toolName}",
                                    timestamp = "Just now",
                                    toolName = event.toolName,
                                )
                                _state.update {
                                    it.copy(
                                        messages = it.messages + toolMsg,
                                        currentStatus = "Synthesizing weather conditions...",
                                        activeToolName = null,
                                    )
                                }
                            }

                            is AgentEvent.TextDelta -> {
                                responseBuilder.append(event.text)
                                val currentText = responseBuilder.toString()

                                _state.update { state ->
                                    if (!initialAgentMessageAdded) {
                                        initialAgentMessageAdded = true
                                        val newMsg = ChatMessage(
                                            id = agentMsgId,
                                            sender = MessageSender.AGENT,
                                            text = currentText,
                                            timestamp = "Just now",
                                        )
                                        state.copy(messages = state.messages + newMsg)
                                    } else {
                                        val updatedMessages = state.messages.map { msg ->
                                            if (msg.id == agentMsgId) msg.copy(text = currentText) else msg
                                        }
                                        state.copy(messages = updatedMessages)
                                    }
                                }
                            }

                            is AgentEvent.RunCompleted -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        currentStatus = null,
                                        activeToolName = null,
                                    )
                                }
                            }

                            is AgentEvent.RunFailed -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "Ollama Agent Failed: ${event.error.message}",
                                        currentStatus = null,
                                        activeToolName = null,
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to run Ollama agent: ${e.message}",
                        currentStatus = null,
                        activeToolName = null,
                    )
                }
            }
        }
    }

    /**
     * Fallback execution flow: executes the two registered tools directly via Ktor
     * using the FreeOpenAPI weather service, delivering a real-time structured report.
     */
    private suspend fun executeDirectToolFlow(userPrompt: String) {
        try {
            val countryGuess = userPrompt
                .replace(Regex("(?i)what('?s| is) the weather in"), "")
                .replace(Regex("(?i)weather (in|for)"), "")
                .replace(Regex("(?i)weather"), "")
                .trim()
                .removeSuffix("?")
                .ifEmpty { "Egypt" }

            // Step 1: Call Tool 1 (Coordinates & Country info)
            _state.update {
                it.copy(
                    currentStatus = "Tool 1: Locating '$countryGuess'...",
                    activeToolName = "get_country_coordinates",
                )
            }

            val location = weatherService.getCountryCoordinates(countryGuess)

            val tool1Msg = ChatMessage(
                id = nextId("tool-1"),
                sender = MessageSender.TOOL_EVENT,
                text = "Tool 1 [get_country_coordinates]: Found ${location.flagEmoji ?: ""} ${location.name} (${location.latitude}, ${location.longitude})",
                timestamp = "Just now",
                toolName = "get_country_coordinates",
            )
            _state.update { it.copy(messages = it.messages + tool1Msg) }

            // Step 2: Call Tool 2 (Weather Conditions)
            _state.update {
                it.copy(
                    currentStatus = "Tool 2: Querying weather conditions...",
                    activeToolName = "get_weather_conditions",
                )
            }

            val weather = weatherService.getWeatherConditions(location.latitude, location.longitude)

            val tool2Msg = ChatMessage(
                id = nextId("tool-2"),
                sender = MessageSender.TOOL_EVENT,
                text = "Tool 2 [get_weather_conditions]: ${weather.condition}, ${weather.temperatureC}°C",
                timestamp = "Just now",
                toolName = "get_weather_conditions",
            )
            _state.update { it.copy(messages = it.messages + tool2Msg) }

            // Step 3: Format synthesized response
            val flag = location.flagEmoji ?: "📍"
            val capitalStr = location.capital?.let { "• Capital: $it\n" } ?: ""
            val apparentStr = weather.apparentTemperatureC?.let { "• Feels like: $it°C\n" } ?: ""
            val windStr = weather.windSpeedKmh?.let { "• Wind Speed: $it km/h\n" } ?: ""
            val precipitationStr = weather.precipitationMm?.let { "• Precipitation: $it mm\n" } ?: ""

            val responseText = buildString {
                appendLine("### $flag Weather in ${location.name}")
                appendLine()
                append(capitalStr)
                appendLine("• Coordinates: Latitude ${location.latitude}, Longitude ${location.longitude}")
                appendLine("• Condition: ${weather.condition}")
                appendLine("• Temperature: ${weather.temperatureC}°C")
                append(apparentStr)
                append(windStr)
                append(precipitationStr)
            }.trim()

            val agentMsg = ChatMessage(
                id = nextId("agent"),
                sender = MessageSender.AGENT,
                text = responseText,
                timestamp = "Just now",
            )

            _state.update {
                it.copy(
                    messages = it.messages + agentMsg,
                    isLoading = false,
                    currentStatus = null,
                    activeToolName = null,
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = "Could not fetch weather: ${e.message}",
                    currentStatus = null,
                    activeToolName = null,
                )
            }
        }
    }
}
