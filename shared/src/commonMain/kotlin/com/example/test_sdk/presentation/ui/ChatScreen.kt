package com.example.test_sdk.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_sdk.presentation.ChatIntent
import com.example.test_sdk.presentation.ChatMessage
import com.example.test_sdk.presentation.ChatViewModel
import com.example.test_sdk.presentation.MessageSender

// ── White / Light Theme Color Palette ──────────────────────────────────
private val PageBackground  = Color(0xFFF8FAFC)   // Slate 50 clean light background
private val SurfaceWhite    = Color(0xFFFFFFFF)   // Pure white for cards, bars, bubbles
private val BorderSubtle    = Color(0xFFE2E8F0)   // Slate 200 light border
private val AccentPrimary   = Color(0xFF0284C7)   // Sky 600 vibrant primary
private val AccentSecondary = Color(0xFF4F46E5)   // Indigo 600 secondary accent
private val AccentSuccess   = Color(0xFF10B981)   // Emerald 500 status green
private val TextPrimary     = Color(0xFF0F172A)   // Slate 900 high-contrast body
private val TextSecondary   = Color(0xFF475569)   // Slate 600 readable secondary
private val TextMuted       = Color(0xFF94A3B8)   // Slate 400 captions & hints


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = remember { ChatViewModel() },
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // Auto-scroll smoothly when new messages arrive
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = PageBackground,
        topBar = {
            ModernTopBar(
                isLoading = state.isLoading,
                onClearChat = { viewModel.onIntent(ChatIntent.ClearChat) },
            )
        },
        bottomBar = {
            ModernBottomInputBar(
                text = state.inputText,
                isLoading = state.isLoading,
                onTextChange = { viewModel.onIntent(ChatIntent.UpdateInput(it)) },
                onSend = {
                    viewModel.onIntent(ChatIntent.SendMessage(state.inputText))
                    focusManager.clearFocus()
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Error banner if any
            AnimatedVisibility(
                visible = state.error != null,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut(),
            ) {
                state.error?.let { err ->
                    ErrorBanner(
                        error = err,
                        onDismiss = { viewModel.onIntent(ChatIntent.DismissError) },
                    )
                }
            }

            // Live reasoning & tool execution status banner
            AnimatedVisibility(
                visible = state.isLoading && state.currentStatus != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
                exit = fadeOut(),
            ) {
                LiveStatusPill(
                    status = state.currentStatus ?: "",
                    toolName = state.activeToolName,
                )
            }

            // Chat content: Empty Hero State OR Message Thread
            if (state.messages.isEmpty()) {
                EmptyHeroState(
                    onSelectCountry = { countryPrompt ->
                        viewModel.onIntent(ChatIntent.SelectSuggestion(countryPrompt))
                    },
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 14.dp),
                ) {
                    items(state.messages, key = { it.id }) { message ->
                        AnimatedMessageBubble(message = message)
                    }

                    if (state.isLoading) {
                        item {
                            TypingIndicatorBubble()
                        }
                    }
                }
            }
        }
    }
}

/**
 * Modern premium Top Bar with online pulsing status indicator and clear chat action.
 */
@Composable
private fun ModernTopBar(
    isLoading: Boolean,
    onClearChat: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    Surface(
        color = SurfaceWhite,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar Icon with subtle light gradient ring
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFFE0F2FE), Color(0xFFEDE9FE))
                                )
                            )
                            .border(1.dp, Color(0xFFBAE6FD), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🌤️", fontSize = 20.sp)
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Aivo Weather Agent",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                letterSpacing = 0.2.sp,
                            )
                            Spacer(Modifier.width(8.dp))
                            // Status Pulse Dot
                            Box(contentAlignment = Alignment.Center) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .scale(if (isLoading) pulseScale else 1f)
                                        .clip(CircleShape)
                                        .background(
                                            if (isLoading) AccentPrimary.copy(alpha = pulseAlpha)
                                            else AccentSuccess.copy(alpha = 0.25f)
                                        )
                                )
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (isLoading) AccentPrimary else AccentSuccess)
                                )
                            }
                        }

                        Text(
                            text = if (isLoading) "Autonomous Agent Reasoning..." else "Online • Ollama Engine • 2 Live Tools",
                            color = if (isLoading) AccentPrimary else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                // Reset / Clear chat action
                IconButton(
                    onClick = onClearChat,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SurfaceWhite)
                        .border(1.dp, BorderSubtle, CircleShape),
                ) {
                    Text("🗑️", fontSize = 14.sp)
                }
            }

            // Hairline separator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderSubtle)
            )
        }
    }
}

/**
 * Empty Hero State displayed before the conversation starts or when cleared.
 * Provides instant 1-tap country prompts with flags.
 */
@Composable
private fun EmptyHeroState(
    onSelectCountry: (String) -> Unit,
) {
    val quickCountries = listOf(
        Pair("🇪🇬", "Egypt"),
        Pair("🇯🇵", "Japan"),
        Pair("🇫🇷", "France"),
        Pair("🇧🇷", "Brazil"),
        Pair("🇺🇸", "United States"),
        Pair("🇪🇸", "Spain"),
        Pair("🇩🇪", "Germany"),
        Pair("🇦🇺", "Australia"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Floating Hero Badge
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFFE0F2FE), Color(0xFFF0F9FF))
                    )
                )
                .border(1.5.dp, Color(0xFFBAE6FD), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text("🌍", fontSize = 36.sp)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Ask Any Country's Weather",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Powered by Aivo SDK. The agent automatically executes 2 live tools to resolve coordinates and current conditions.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "QUICK SUGGESTIONS",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            color = TextMuted,
        )

        Spacer(Modifier.height(12.dp))

        // Grid-like rows of interactive prompt chips
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            quickCountries.chunked(2).forEach { rowCountries ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    rowCountries.forEach { (flag, country) ->
                        Surface(
                            onClick = { onSelectCountry("Weather in $country") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(flag, fontSize = 18.sp)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = country,
                                    fontSize = 13.sp,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Animated Message Bubble with slide-in & fade transitions.
 */
@Composable
private fun AnimatedMessageBubble(message: ChatMessage) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(250)) + slideInVertically(initialOffsetY = { 20 }, animationSpec = tween(250)),
    ) {
        when (message.sender) {
            MessageSender.USER -> UserBubble(text = message.text)
            MessageSender.AGENT -> AgentBubble(text = message.text)
            MessageSender.TOOL_EVENT -> ToolEventPill(text = message.text)
            MessageSender.SYSTEM -> SystemPill(text = message.text)
        }
    }
}

/**
 * User Message: sleek gradient bubble aligned to the right.
 */
@Composable
private fun UserBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .clip(RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp))
                .background(
                    Brush.linearGradient(
                        listOf(AccentPrimary, Color(0xFF2563EB))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

/**
 * Agent Message: modern light card with structured weather highlights.
 */
@Composable
private fun AgentBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Card(
            shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth(0.92f),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F2FE)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🤖", fontSize = 12.sp)
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Aivo Weather Agent",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = AccentPrimary,
                    )
                }

                // Render content: structured weather details cleanly presented
                Text(
                    text = text,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                )
            }
        }
    }
}

/**
 * Sleek tool execution event pill showing autonomous steps in real-time.
 */
@Composable
private fun ToolEventPill(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF1F5F9),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.padding(vertical = 2.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (text.contains("coordinates", ignoreCase = true)) "📍" else "⛅",
                    fontSize = 12.sp,
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = text,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF0369A1),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun SystemPill(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        color = TextMuted,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
    )
}

/**
 * Animated Typing / Reasoning Indicator with 3 smooth bouncing dots.
 */
@Composable
private fun TypingIndicatorBubble() {
    val infiniteTransition = rememberInfiniteTransition()

    val dot1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, delayMillis = 0),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val dot2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, delayMillis = 150),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val dot3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, delayMillis = 300),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    Row(
        modifier = Modifier.padding(start = 4.dp, top = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWhite,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Box(Modifier.offset(y = dot1.dp).size(6.dp).clip(CircleShape).background(AccentPrimary))
                Box(Modifier.offset(y = dot2.dp).size(6.dp).clip(CircleShape).background(AccentPrimary))
                Box(Modifier.offset(y = dot3.dp).size(6.dp).clip(CircleShape).background(AccentPrimary))
            }
        }
    }
}

/**
 * Live reasoning status banner showing active tool calls.
 */
@Composable
private fun LiveStatusPill(status: String, toolName: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp),
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF0F9FF),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBAE6FD)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(14.dp),
                    strokeWidth = 2.dp,
                    color = AccentPrimary,
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0369A1),
                    )
                    if (toolName != null) {
                        Text(
                            text = "Aivo Tool: $toolName",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = AccentPrimary,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Error Banner with dismiss action.
 */
@Composable
private fun ErrorBanner(error: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "⚠️ $error",
                color = Color(0xFF991B1B),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = onDismiss) {
                Text("Dismiss", color = Color(0xFFDC2626), fontSize = 12.sp)
            }
        }
    }
}

/**
 * Modern floating Input Bar handling IME keyboard padding & navigation bar padding.
 */
@Composable
private fun ModernBottomInputBar(
    text: String,
    isLoading: Boolean,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
) {
    Surface(
        color = SurfaceWhite,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        Column {
            // Subtle top outline
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderSubtle)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                // Expandable rounded input pill (fits 1 to 3 lines, then scrolls)
                OutlinedTextField(
                    value = text,
                    onValueChange = onTextChange,
                    placeholder = {
                        Text(
                            text = "Ask about any country (e.g. Egypt, Japan)...",
                            fontSize = 13.sp,
                            color = TextMuted,
                        )
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(22.dp),
                    minLines = 1,
                    maxLines = 3,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PageBackground,
                        unfocusedContainerColor = PageBackground,
                        disabledContainerColor = PageBackground.copy(alpha = 0.5f),
                        focusedBorderColor = AccentPrimary,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = AccentPrimary,
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (text.isNotBlank() && !isLoading) onSend()
                        }
                    ),
                )

                Spacer(Modifier.width(10.dp))

                // Modern Circular Send Button pinned to the bottom
                val canSend = text.isNotBlank() && !isLoading
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .padding(bottom = 2.dp)
                        .clip(CircleShape)
                        .background(
                            if (canSend) {
                                Brush.linearGradient(listOf(AccentPrimary, Color(0xFF2563EB)))
                            } else {
                                Brush.linearGradient(listOf(Color(0xFFF1F5F9), Color(0xFFF1F5F9)))
                            }
                        )
                        .border(
                            1.dp,
                            if (canSend) AccentPrimary.copy(alpha = 0.5f) else BorderSubtle,
                            CircleShape,
                        )
                        .clickable(enabled = canSend, onClick = onSend),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = Color.White,
                        )
                    } else {
                        Text(
                            text = "➤",
                            fontSize = 16.sp,
                            color = if (canSend) Color.White else TextMuted,
                        )
                    }
                }
            }
        }
    }
}
