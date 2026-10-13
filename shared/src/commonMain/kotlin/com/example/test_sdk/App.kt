package com.example.test_sdk

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_sdk.presentation.ui.ChatScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        ChatScreen()
    }
}