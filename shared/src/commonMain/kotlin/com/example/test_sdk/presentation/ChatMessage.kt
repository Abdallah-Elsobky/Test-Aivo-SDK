package com.example.test_sdk.presentation

enum class MessageSender {
    USER,
    AGENT,
    TOOL_EVENT,
    SYSTEM,
}

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: String,
    val toolName: String? = null,
)
