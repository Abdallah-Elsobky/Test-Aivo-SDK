package com.example.test_sdk.presentation

import com.aivo.sdk.AivoProvider

data class ChatState(
    val messages: List<ChatMessage> = listOf(
        ChatMessage(
            id = "welcome",
            sender = MessageSender.AGENT,
            text = "Hello! I am your Aivo Weather Agent. 🌍\n\nAsk me about the weather in any country (e.g. \"What is the weather in Egypt?\"), and I will automatically:\n1. Look up the country's coordinates using tool 1\n2. Query current weather conditions using tool 2\n3. Deliver a complete report with temperature, conditions, and coordinates!",
            timestamp = "Just now",
        )
    ),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val currentStatus: String? = null,
    val activeToolName: String? = null,
    val apiKey: String = "",
    val selectedProvider: AivoProvider = AivoProvider.GEMINI,
    val showSettings: Boolean = false,
    val error: String? = null,
)

sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    data class UpdateInput(val text: String) : ChatIntent
    data class UpdateApiKey(val key: String) : ChatIntent
    data class SelectProvider(val provider: AivoProvider) : ChatIntent
    data class SelectSuggestion(val prompt: String) : ChatIntent
    data class ToggleSettings(val show: Boolean) : ChatIntent
    data object ClearChat : ChatIntent
    data object DismissError : ChatIntent
}
