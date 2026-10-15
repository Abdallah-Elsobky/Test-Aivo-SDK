package com.example.test_sdk

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_sdk.presentation.ui.ChatScreen

private val AivoLightColors = lightColorScheme(
    primary            = Color(0xFF0284C7),
    onPrimary          = Color(0xFFFFFFFF),
    primaryContainer   = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF0369A1),
    secondary          = Color(0xFF4F46E5),
    onSecondary        = Color(0xFFFFFFFF),
    background         = Color(0xFFF8FAFC),
    onBackground       = Color(0xFF0F172A),
    surface            = Color(0xFFFFFFFF),
    onSurface          = Color(0xFF0F172A),
    surfaceVariant     = Color(0xFFF1F5F9),
    onSurfaceVariant   = Color(0xFF475569),
    outline            = Color(0xFFE2E8F0),
    error              = Color(0xFFDC2626),
    onError            = Color(0xFFFFFFFF),
)

@Composable
@Preview
fun App() {
    println("roto: App() composable invoked")
    MaterialTheme(colorScheme = AivoLightColors) {
        ChatScreen()
    }
}