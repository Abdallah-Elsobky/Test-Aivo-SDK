package com.example.test_sdk.agent

import com.aivo.sdk.AivoProvider
import com.aivo.sdk.AivoSdk
import com.aivo.sdk.core.model.ProviderModel
import com.aivo.sdk.provider.ollama.OllamaModel
import com.aivo.sdk.runtime.agent.AgentDefinition
import com.example.test_sdk.data.network.WeatherRemoteService

object WeatherAgentFactory {

    const val AGENT_ID = "weather_assistant"

    /**
     * Creates an [AivoSdk] instance managing the weather agent and its 2 tools.
     *
     * Flow:
     * 1. User asks about a country.
     * 2. The agent calls 'get_country_coordinates' tool to fetch lat/lon.
     * 3. The agent calls 'get_weather_conditions' tool using those coordinates.
     * 4. The agent returns a synthesized response with coordinates and current conditions.
     */
    fun create(
        apiKey: String,
        provider: AivoProvider = AivoProvider.OLLAMA,
        modelName: String = OllamaModel.GPT_OSS_120B.modelId,
        service: WeatherRemoteService = WeatherRemoteService(),
    ): AivoSdk {
        val coordsTool = WeatherTools.createCountryCoordinatesTool(service)
        val weatherTool = WeatherTools.createWeatherConditionsTool(service)

        val providerPrefix = when (provider) {
            AivoProvider.GEMINI -> "gemini"
            AivoProvider.OPEN_ROUTER -> "openrouter"
            AivoProvider.OLLAMA -> "ollama"
            else -> "openai"
        }

        return AivoSdk {
            providers {
                when (provider) {
                    AivoProvider.GEMINI -> gemini("gemini") {
                        apiKey(apiKey)
                    }
                    AivoProvider.OPEN_ROUTER -> openRouter("openrouter") {
                        apiKey(apiKey)
                    }
                    AivoProvider.OLLAMA -> ollama("ollama") {
                        if (apiKey.isNotBlank()) apiKey(apiKey)
                    }
                    else -> openAiCompatible("openai") {
                        apiKey(apiKey)
                    }
                }
            }

            defaultModel("$providerPrefix:$modelName")

            tools {
                register(coordsTool)
                register(weatherTool)
            }

            agents {
                define(AGENT_ID) {
                    name = "Weather Assistant"
                    systemPrompt = """
                        You are a specialized Weather Assistant for global countries.
                        When asked about the weather or conditions for a country:
                        1. Always first call the 'get_country_coordinates' tool to retrieve the country's latitude, longitude, capital, and flag.
                        2. Next, call the 'get_weather_conditions' tool using the extracted latitude and longitude to get the current temperature, wind speed, precipitation, and sky conditions.
                        3. Finally, formulate a friendly, concise, and beautifully structured response containing:
                           - Country Name, Official Name & Flag Emoji
                           - Capital city & Coordinates (Latitude, Longitude)
                           - Current Weather Condition & Temperature (in Celsius and apparent)
                           - Wind Speed & Precipitation
                    """.trimIndent()
                    tools(coordsTool, weatherTool)
                }
            }

            entryAgent(AGENT_ID)

            runtime {
                maxSteps = 8
            }
        }
    }
}
