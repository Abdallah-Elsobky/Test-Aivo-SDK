package com.example.test_sdk.presentation

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val currentStatus: String? = null,
    val activeToolName: String? = null,
    val error: String? = null,
)

sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    data class UpdateInput(val text: String) : ChatIntent
    data class SelectSuggestion(val prompt: String) : ChatIntent
    data object ClearChat : ChatIntent
    data object DismissError : ChatIntent
}
