# 🚀 Aivo SDK — LinkedIn Carousel & Post Content Blueprint

> **File Target:** LinkedIn Document Post / Carousel & Accompanying Post Copy  
> **Topic:** Introducing **Aivo SDK** — Production-Grade Autonomous Agent Loop & Lifecycle Engine for Kotlin Multiplatform (Android, JVM, iOS)  
> **Format:** Structured Slide-by-Slide (`img1`, `img2`, ... `img17`) with minimal, clean code snippets, hooky copy, and direct extraction blocks. Part 1 covers Core Engine Highlights (`img1`–`img10`), and Part 2 delivers the End-to-End Multi-Agent Master Example (`img11`–`img17`).

---

## 📱 Complete LinkedIn Post Copy (Accompanying Text)

```text
Stop writing fragile while(true) loops to run AI agents. 🛑

Building an autonomous agent isn't just sending a one-off prompt to an LLM. It's managing the entire Agent Execution Cycle:
1️⃣ Prompt & Context assembly
2️⃣ LLM reasoning & tool decision
3️⃣ Type-safe tool invocation & argument validation
4️⃣ Injecting tool results back into the thought loop
5️⃣ Guarding against infinite recursion & cycle deadlocks
6️⃣ Memory budget trimming & token sliding
7️⃣ Live token & status streaming to mobile/desktop UIs

Most developers rewrite this fragile state machine from scratch for every single project — and have to do it all over again for Android and iOS.

Meet Aivo SDK 🤖: The open-source Kotlin Multiplatform engine that handles the entire agent loop, manages multi-agent teams, and runs natively across Android, iOS, and JVM.

What Aivo SDK manages out of the box:
✅ Autonomous Execution Loop: Handles multi-turn tool calling and re-prompting automatically
✅ Multi-Agent Squads: Supervisor-worker coordination with automated cycle detection
✅ True Cross-Platform: One single engine runs in Android apps, JVM backends, and iOS commonMain
✅ Provider Agnostic: Swap between Gemini, Ollama, OpenRouter, or private vLLM servers in 1 line
✅ Reactive Flow Streaming: Live text tokens, reasoning deltas, and tool statuses for Jetpack Compose & Swift
✅ Production Guardrails: Sliding window memory, step limits, backoff retries, and zero-leak SecretString

Swipe through the carousel below to see how Aivo SDK takes the pain out of managing agent cycles! 👇

📦 Maven Central: io.github.abdallah-elsobky:aivo-sdk:1.0.1
🌐 Interactive Docs: https://abdallah-elsobky.github.io/Aivo-SDK/
⭐ GitHub: https://github.com/Abdallah-Elsobky/Aivo-SDK

#AIAgents #Kotlin #KotlinMultiplatform #AndroidDev #iOSDev #SoftwareArchitecture #ArtificialIntelligence #MobileDev #OpenSource #Gemini #Ollama
```

---

## 📑 Slide-by-Slide Carousel Specification

---

### 🖼️ `img1` — The Hook: Stop Coding the Agent Loop by Hand

#### 🏷️ Slide Metadata
- **Slide ID:** `img1`
- **Slide Type:** Cover / Hook Slide
- **Headline:** Stop Coding the Agent Cycle by Hand.
- **Sub-headline:** Calling an LLM is easy. Managing the reasoning loop, tool execution, and recursion is brutal.

#### 📝 Text Content
```text
Building autonomous agents shouldn't mean writing fragile while(true) loops, manual JSON parsers, and custom state machines.

Meet Aivo SDK: The production-grade engine that orchestrates the entire agent cycle across Android, iOS & JVM.
```

#### 💻 Code Snippet
```kotlin
// Android, iOS & JVM — Autonomous loop in 3 lines
val aivo = AivoSdk.create(
    provider = AivoProvider.GEMINI,
    model    = GeminiModel.GEMINI_3_8_FLASH.modelId,
    apiKey   = BuildConfig.GEMINI_API_KEY
)
```

#### 🎨 Visual Design Notes
- **Theme:** Dark futuristic glassmorphism gradient (deep navy `#0B132B` to cyber violet `#3A0CA3`).
- **Badges:** `Autonomous Agent Loop` | `Cycle Detection` | `Kotlin Multiplatform`.
- **Centerpiece:** Aivo SDK robot logo with glowing loop accent.
- **Hook Footer:** "Swipe to see the agent cycle in 30 seconds ➔"

```json
{
  "id": "img1",
  "tag": "AUTONOMOUS AGENTS",
  "title": "Stop Coding the Agent Cycle by Hand",
  "subtitle": "Calling an LLM is easy. Managing the autonomous loop, tool execution, and memory is brutal. Meet Aivo SDK.",
  "fileName": "AivoSdk.kt",
  "lang": "kotlin",
  "swipeText": "See the 30-Sec Cycle ➔",
  "code": "// Android, iOS & JVM — Autonomous loop in 3 lines\nval aivo = AivoSdk.create(\n    provider = AivoProvider.GEMINI,\n    model    = GeminiModel.GEMINI_3_8_FLASH.modelId,\n    apiKey   = BuildConfig.GEMINI_API_KEY\n)"
}
```

---

### 🖼️ `img2` — The 30-Second Agent Cycle

#### 🏷️ Slide Metadata
- **Slide ID:** `img2`
- **Slide Type:** Fast-Start Demo
- **Headline:** The Complete Agent Cycle in 30 Seconds.
- **Sub-headline:** Zero boilerplate. Aivo SDK manages the reasoning loop and returns structured output.

#### 📝 Text Content
```text
Say goodbye to hundreds of lines of boilerplate HTTP clients, JSON parsers, and custom chat loops.

Initialize with one line. Aivo SDK drives the conversation loop, context retention, and error recovery automatically.
```

#### 💻 Code Snippet
```kotlin
// 1. Initialize client with autonomous loop
val aivo = AivoSdk.create(
    model  = OllamaModel.GPT_OSS_120B,
    apiKey = BuildConfig.OLLAMA_API_KEY
)

// 2. Chat with your agent
val response = aivo.chat(ConversationId("session-1"), "Explain KMP in one sentence")
println(response.text)
```

#### 🎨 Visual Design Notes
- **Callout Tag:** `⚡ 30-Second Quickstart`
- **Side Card:** Show execution flow: User Prompt ➔ Aivo Agent Loop ➔ Output.
- **Accent Color:** Emerald green `#10B981` (representing speed and reliability).

```json
{
  "id": "img2",
  "tag": "AGENT LIFECYCLE",
  "title": "The Complete Agent Cycle in 30 Seconds",
  "subtitle": "Zero boilerplate. Aivo SDK drives the conversation loop and state retention automatically.",
  "fileName": "QuickStart.kt",
  "lang": "kotlin",
  "swipeText": "See Setup in Slide 3 ➔",
  "code": "// 1. Initialize client with autonomous loop\nval aivo = AivoSdk.create(\n    model  = OllamaModel.GPT_OSS_120B,\n    apiKey = BuildConfig.OLLAMA_API_KEY\n)\n\n// 2. Chat with your agent\nval response = aivo.chat(ConversationId(\"s1\"), \"Explain KMP in one sentence\")\nprintln(response.text)"
}
```

---

### 🖼️ `img3` — One Agent Engine Across Android, iOS & JVM

#### 🏷️ Slide Metadata
- **Slide ID:** `img3`
- **Slide Type:** Multiplatform Engine
- **Headline:** One Agent Engine for Android, iOS & JVM.
- **Sub-headline:** Write your agent loop once in commonMain. Available directly on Maven Central.

#### 📝 Text Content
```text
Why rewrite your agent logic in Swift for iOS and Python for backend?

Aivo SDK is pure Kotlin Multiplatform. One single dependency covers Android mobile apps, backend JVM microservices, and iOS commonMain.
```

#### 💻 Code Snippet
```kotlin
// Android (app/build.gradle.kts) or KMP commonMain
dependencies {
    implementation("io.github.abdallah-elsobky:aivo-sdk:1.0.1")
}

// Write your agent loop once — run everywhere natively
```

#### 🎨 Visual Design Notes
- **Badges:** `Maven Central` | `Android API 24+` | `iOS Native` | `JVM 11+`.
- **Design Graphic:** Gradle icon with checkmark banner.
- **Takeaway:** Universal agent lifecycle engine with zero platform bridges.

```json
{
  "id": "img3",
  "tag": "CROSS-PLATFORM",
  "title": "One Agent Engine for Android, iOS & JVM",
  "subtitle": "Write your agent loop once in commonMain. Available directly on Maven Central.",
  "fileName": "build.gradle.kts",
  "lang": "kotlin",
  "swipeText": "Discover Providers ➔",
  "code": "// Android (app/build.gradle.kts) or KMP commonMain\ndependencies {\n    implementation(\"io.github.abdallah-elsobky:aivo-sdk:1.0.1\")\n}\n\n// Write your agent loop once — run everywhere natively"
}
```

---

### 🖼️ `img4` — Provider Agnostic Execution Loop

#### 🏷️ Slide Metadata
- **Slide ID:** `img4`
- **Slide Type:** Core Feature: Provider Flexibility
- **Headline:** Swap Models Without Breaking the Loop.
- **Sub-headline:** Switch between Gemini, Ollama, OpenRouter, and Local LLMs with 1 line.

#### 📝 Text Content
```text
Need high-speed Gemini Flash for mobile?
Local private Ollama for enterprise security?
OpenRouter for 200+ open-source models?

Your agent execution loop, tools, and memory remain 100% untouched. Change one parameter — zero code rewrites.
```

#### 💻 Code Snippet
```kotlin
// 1. Google Gemini Flash
val gemini = AivoSdk.create(
    provider = AivoProvider.GEMINI,
    model    = GeminiModel.GEMINI_3_8_FLASH.modelId,
    apiKey   = BuildConfig.GEMINI_API_KEY
)

// 2. Ollama Cloud or Local (Same agent loop!)
val ollama = AivoSdk.create(
    provider = AivoProvider.OLLAMA,
    model    = OllamaModel.GPT_OSS_120B.modelId,
    apiKey   = BuildConfig.OLLAMA_API_KEY
)
```

#### 🎨 Visual Design Notes
- **Visuals:** Provider logos (Gemini, Ollama, OpenRouter, OpenAI, vLLM).
- **Badge:** `Type-Safe Enums + Custom Endpoint Support`.

```json
{
  "id": "img4",
  "tag": "ZERO VENDOR LOCK-IN",
  "title": "Swap Models Without Breaking the Loop",
  "subtitle": "Your agent loop stays 100% identical across Gemini, Ollama, OpenRouter, or private vLLM.",
  "fileName": "Providers.kt",
  "lang": "kotlin",
  "swipeText": "Check Out Tools ➔",
  "code": "// 1. Google Gemini Flash\nval gemini = AivoSdk.create(provider = AivoProvider.GEMINI, model = GeminiModel.GEMINI_3_8_FLASH.modelId, ...)\n\n// 2. Ollama Cloud or Local (Same agent loop!)\nval ollama = AivoSdk.create(provider = AivoProvider.OLLAMA, model = OllamaModel.GPT_OSS_120B.modelId, ...)"
}
```

---

### 🖼️ `img5` — Autonomous Tool Execution in the Cycle

#### 🏷️ Slide Metadata
- **Slide ID:** `img5`
- **Slide Type:** Core Feature: Tool Execution
- **Headline:** Tools That Run Inside the Cycle Automatically.
- **Sub-headline:** The engine parses args, runs Kotlin lambdas, feeds results back, and resumes reasoning.

#### 📝 Text Content
```text
LLMs are passive text generators without tools. But executing tools manually is tedious and error-prone.

Aivo SDK auto-generates JSON schemas from Kotlin lambdas, detects tool requests, executes functions, and reinjects results into the reasoning loop.
```

#### 💻 Code Snippet
```kotlin
// Type-safe tool definition in pure Kotlin
val weatherTool = tool("get_weather", "Fetch weather for a city") {
    param("city", "Target city name", ParamType.String, required = true)

    execute { args ->
        weatherService.getForecast(args.string("city"))
    }
}

// Attach tool to agent — Aivo runs it inside the loop
val aivo = AivoSdk {
    tools { +weatherTool }
    agents {
        define("assistant") {
            systemPrompt = "You are a helpful travel assistant."
            tools("get_weather")
        }
    }
}
```

#### 🎨 Visual Design Notes
- **Highlight Box:** `🛡️ High-Risk Protection`: Built-in human confirmation gates for sensitive actions.
- **Accent Color:** Cyber Cyan `#00F5D4`.

```json
{
  "id": "img5",
  "tag": "TYPE-SAFE TOOLS",
  "title": "Tools That Run Inside the Cycle Automatically",
  "subtitle": "Aivo SDK auto-generates schemas, runs lambdas, and reinjects results into the loop.",
  "fileName": "Tools.kt",
  "lang": "kotlin",
  "swipeText": "See Multi-Agent Squads ➔",
  "code": "val weatherTool = tool(\"get_weather\", \"Fetch weather for city\") {\n    param(\"city\", \"Target city name\", ParamType.String, required = true)\n    execute { args -> weatherService.getForecast(args.string(\"city\")) }\n}"
}
```

---

### 🖼️ `img6` — Multi-Agent Squads & Cycle Detection

#### 🏷️ Slide Metadata
- **Slide ID:** `img6`
- **Slide Type:** Core Feature: Multi-Agent Teams
- **Headline:** Manage Agent Squads with Built-in Loop Guards.
- **Sub-headline:** Supervisor delegation with automatic recursion and cycle detection.

#### 📝 Text Content
```text
Don't overload a single prompt. Split complex missions across specialized agents.

Aivo SDK includes a supervisor orchestrator: the coordinator delegates sub-tasks, collects results, and prevents runaway infinite loops with built-in cycle detection.
```

#### 💻 Code Snippet
```kotlin
val aivo = AivoSdk {
    agents {
        // Supervisor orchestrates the squad
        define("supervisor") {
            systemPrompt = "Delegate research to 'researcher' and drafting to 'writer'."
            delegates("researcher", "writer")
        }

        // Specialized sub-agents
        define("researcher") { tools("web_search") }
        define("writer")     { systemPrompt = "Write engaging copy." }
    }
    entryAgent("supervisor")
}
```

#### 🎨 Visual Design Notes
- **Visual Diagram:** `User Prompt` ➔ `[Supervisor]` ⇄ `[Researcher]` / `[Writer]` ➔ `Final Answer`.
- **Security Callout:** Auto-cycle detection halts circular delegations before they drain budgets.

```json
{
  "id": "img6",
  "tag": "MULTI-AGENT SQUADS",
  "title": "Manage Agent Squads with Built-in Loop Guards",
  "subtitle": "Supervisor delegation with built-in cycle detection to stop infinite loops.",
  "fileName": "Supervisor.kt",
  "lang": "kotlin",
  "swipeText": "See Streaming Flow ➔",
  "code": "define(\"supervisor\") {\n    systemPrompt = \"Delegate tasks to researcher and writer.\"\n    delegates(\"researcher\", \"writer\")\n}\ndefine(\"researcher\") { tools(\"web_search\") }\ndefine(\"writer\")     { systemPrompt = \"Write clean copy.\" }"
}
```

---

### 🖼️ `img7` — Live Telemetry & Streaming the Cycle

#### 🏷️ Slide Metadata
- **Slide ID:** `img7`
- **Slide Type:** Core Feature: Real-time UI Streaming
- **Headline:** Stream Every Beat of the Agent's Thought Cycle.
- **Sub-headline:** Powered by Kotlin Flow for Jetpack Compose and SwiftUI.

#### 📝 Text Content
```text
Users hate waiting for a 10-second agent run to finish in total silence.

Aivo SDK emits strongly-typed events via Kotlin Flow: live text deltas, chain-of-thought reasoning, and tool execution phases in real time.
```

#### 💻 Code Snippet
```kotlin
// Collect agent lifecycle events in your ViewModel
aivo.stream(ConversationId("stream-1"), "Analyze quarterly report")
    .collect { event ->
        when (event) {
            is AgentEvent.TextDelta       -> uiState.append(event.text)       // Live tokens
            is AgentEvent.ReasoningDelta  -> uiState.showThinking(event.text) // CoT
            is AgentEvent.ToolCallStarted -> uiState.showSpinner(event.toolName)
            is AgentEvent.RunCompleted    -> uiState.markDone()
            is AgentEvent.RunFailed       -> uiState.showError(event.error.message)
        }
    }
```

#### 🎨 Visual Design Notes
- **Preview:** Left: Kotlin Compose code. Right: Smartphone showing live reasoning typewriter.
- **Bonus:** Includes Swift bridge (`FlowAdapter` + `CancellableJob`) for iOS.

```json
{
  "id": "img7",
  "tag": "REACTIVE STREAMING",
  "title": "Stream Every Beat of the Agent's Thought Cycle",
  "subtitle": "Stream live text tokens, reasoning steps, and tool execution directly via Kotlin Flow.",
  "fileName": "Streaming.kt",
  "lang": "kotlin",
  "swipeText": "See Declarative Blueprints ➔",
  "code": "aivo.stream(ConversationId(\"s-1\"), \"Analyze report\").collect { event ->\n    when (event) {\n        is AgentEvent.TextDelta       -> appendText(event.text)\n        is AgentEvent.ToolCallStarted -> showToolStatus(event.toolName)\n        is AgentEvent.RunCompleted    -> onFinished()\n    }\n}"
}
```

---

### 🖼️ `img8` — Declarative Agent Blueprints

#### 🏷️ Slide Metadata
- **Slide ID:** `img8`
- **Slide Type:** Developer Experience (DX)
- **Headline:** Configure Agent Cycles Without Recompiling.
- **Sub-headline:** Define agent prompts, tools, and max step limits in clean Markdown or JSON.

#### 📝 Text Content
```text
Tired of recompiling and redeploying your mobile app just to tweak an agent's prompt or step count?

Store agent definitions in Markdown frontmatter. Prompt engineers and product managers can tune cycles without touching Kotlin code.
```

#### 💻 Code Snippet
```markdown
<!-- assets/agents/analyst.md -->
---
id: analyst
name: Financial Analyst
model: ollama:gpt-oss-120b
tools:
  - calculate_roi
maxSteps: 10
---
You are a senior financial analyst. Always provide data-backed rationale.
```

```kotlin
// Load agent blueprint effortlessly in Kotlin:
val aivo = AivoSdk {
    agents { fromMarkdownPath("agents/analyst.md") }
    entryAgent("analyst")
}
```

#### 🎨 Visual Design Notes
- **Split Visual:** Left shows `.md` frontmatter file. Right shows the clean 3-line Kotlin loader.
- **Value Prop:** Fast iteration, runtime updates, zero recompilation delays.

```json
{
  "id": "img8",
  "tag": "DECLARATIVE DX",
  "title": "Configure Agent Cycles Without Recompiling",
  "subtitle": "Define agent prompts, tools, and step limits in clean Markdown frontmatter or JSON.",
  "fileName": "Blueprint.md",
  "lang": "markdown",
  "swipeText": "Check Out Guardrails ➔",
  "code": "// assets/agents/analyst.md\n---\nid: analyst\ntools: [calc_roi]\nmaxSteps: 10\n---\nYou are a financial analyst.\n\n// Load in Kotlin:\nagents { fromMarkdownPath(\"agents/analyst.md\") }"
}
```

---

### 🖼️ `img9` — Production Guardrails & Loop Safety

#### 🏷️ Slide Metadata
- **Slide ID:** `img9`
- **Slide Type:** Production Readiness & Security
- **Headline:** Guardrails: Sliding Memory & Loop Limits.
- **Sub-headline:** Prevent context overflow, budget leaks, and logcat credential leaks.

#### 📝 Text Content
```text
Amateur AI loops crash when context windows explode or leak API keys into logcat dumps.

Aivo SDK provides battle-tested safety policies:
1. Memory Strategies: Sliding-Window and Token-Budget trimming.
2. Resilience: Exponential backoff with jitter.
3. SecretString: Credentials masked as [REDACTED] everywhere.
```

#### 💻 Code Snippet
```kotlin
val aivo = AivoSdk {
    // 1. Sliding window context management
    memory {
        slidingWindow(maxMessages = 20)
    }

    // 2. Exponential backoff with jitter
    resilience {
        retry {
            maxAttempts = 3
            baseDelayMs = 1_000L
        }
    }
}

// 3. Security: Keys never leak in logs or crash dumps
val secret = SecretString("AIzaSy...")
println(secret) // ➔ "[REDACTED]"
```

#### 🎨 Visual Design Notes
- **Security Shield Icon:** Illustrating credential protection and context safety.
- **Pill Tags:** `Sliding Memory` | `Exponential Retry` | `Zero-Leak SecretString`.

```json
{
  "id": "img9",
  "tag": "PRODUCTION SAFETY",
  "title": "Guardrails: Sliding Memory & Loop Limits",
  "subtitle": "Prevent context overflow and budget drains with sliding memory, retries, and SecretString.",
  "fileName": "Guardrails.kt",
  "lang": "kotlin",
  "swipeText": "See Offline Testing ➔",
  "code": "memory {\n    slidingWindow(maxMessages = 20)\n}\nresilience {\n    retry { maxAttempts = 3; baseDelayMs = 1000L }\n}\nval key = SecretString(\"AIza...\")\nprintln(key) // [REDACTED]"
}
```

---

### 🖼️ `img10` — Deterministic Offline Testing for $0

#### 🏷️ Slide Metadata
- **Slide ID:** `img10`
- **Slide Type:** Software Quality & Testability
- **Headline:** Deterministic Agent Testing in Milliseconds.
- **Sub-headline:** 100% offline unit testing with FakeLlmProvider — 0 network calls, $0 cost.

#### 📝 Text Content
```text
Don't test agent workflows by making real API calls to OpenAI or Gemini. They're slow, flaky, and expensive.

Aivo SDK's Hexagonal Architecture includes FakeLlmProvider: verify complex multi-turn reasoning loops deterministically in milliseconds.
```

#### 💻 Code Snippet
```kotlin
// Fast, deterministic unit testing — 0 network calls, $0 cost!
@Test
fun `agent executes weather flow correctly`() = runTest {
    val fakeLlm = FakeLlmProvider.scripted(
        id       = ProviderId("fake"),
        response = Message.assistant("The weather in Cairo is 28°C.")
    )

    val sdk = AivoSdk {
        providers { register(fakeLlm) }
        defaultModel("fake:model")
    }

    val result = sdk.chat(ConversationId("test-1"), "Weather check")
    assertEquals("The weather in Cairo is 28°C.", result.text)
}
```

#### 🎨 Visual Design Notes
- **Hexagon Diagram:** Core Domain Ports surrounded by Mock LLM Adapters.
- **Metrics Tag:** `100% Deterministic` | `0 Flaky Tests` | `$0 API Cost`.

```json
{
  "id": "img10",
  "tag": "TEST ARCHITECTURE",
  "title": "Deterministic Agent Testing in Milliseconds",
  "subtitle": "Hexagonal Ports & Adapters with FakeLlmProvider for 100% offline testing.",
  "fileName": "AgentTest.kt",
  "lang": "kotlin",
  "swipeText": "Ready to Ship? ➔",
  "code": "@Test\nfun testAgent() = runTest {\n    val fake = FakeLlmProvider.scripted(response = Message.assistant(\"Hello!\"))\n    val sdk = AivoSdk { providers { register(fake) }; defaultModel(\"fake:m\") }\n    assertEquals(\"Hello!\", sdk.chat(ConversationId(\"t\"), \"hi\").text)\n}"
}
```

---

## 🧠 Part 2: End-to-End Master Example — Multi-Tool & Multi-Agent Cycle Walkthrough

---

### 🖼️ `img11` — Master Example: Multi-Agent Squad Architecture

#### 🏷️ Slide Metadata
- **Slide ID:** `img11`
- **Slide Type:** End-to-End Architecture Hook
- **Headline:** Master Example: Multiple Agents & Tools in 1 Cycle.
- **Sub-headline:** How a Supervisor, 2 Specialists, and multiple tools collaborate in one execution cycle.

#### 📝 Text Content
```text
Real-world AI systems aren't single-prompt chatbots. They are coordinated squads of specialized agents.

In this step-by-step masterclass, we build a production E-Commerce Squad:
1️⃣ Multiple Tools: Read-only order lookups + high-risk financial write actions
2️⃣ Multiple Specialists: Orders Agent + Billing Agent + Orchestrating Supervisor
3️⃣ One Unified Cycle: Connected via Aivo SDK with zero boilerplate
4️⃣ Full Loop Handling: Reactive Flow event streaming from ticket to resolution
```

#### 💻 Code Snippet
```kotlin
// Multi-Agent E-Commerce Squad Architecture
//
// [Customer Ticket] ➔ [Support Supervisor]
//                             │
//             ┌───────────────┴───────────────┐
//             ▼                               ▼
//     [Orders Specialist]             [Finance Specialist]
//       └─ fetch_order                  └─ process_refund (Risk Gate)
//               │                               │
//           └───────────────┬───────────────┘
//                             ▼
//                [Verified Resolution in 1 Cycle]
```

#### 🎨 Visual Design Notes
- **Flow Visual:** Clean branching tree diagram from Customer Ticket down to Supervisor, branching to Orders & Finance Specialists, converging on Verified Resolution.
- **Badge Tags:** `Supervisor Pattern` | `Isolated Context` | `Automated Delegation`.
- **Card Gradient:** Midnight Blue (`#0B132B`) to Deep Indigo (`#1D2D50`).

```json
{
  "id": "img11",
  "tag": "SQUAD ARCHITECTURE",
  "title": "Master Example: Multiple Agents & Tools in 1 Cycle",
  "subtitle": "How a Supervisor, 2 Specialists, and multiple tools collaborate in a single, safe execution cycle.",
  "fileName": "Architecture.kt",
  "lang": "kotlin",
  "swipeText": "Step 1: Build Tools ➔",
  "code": "// Multi-Agent E-Commerce Squad Architecture\n//\n// [Customer Ticket] ➔ [Support Supervisor]\n//                             │\n//             ┌───────────────┴───────────────┐\n//             ▼                               ▼\n//     [Orders Specialist]             [Finance Specialist]\n//       └─ fetch_order                  └─ process_refund (Risk Gate)\n//             │                               │\n//             └───────────────┬───────────────┘\n//                             ▼\n//                [Verified Resolution in 1 Cycle]"
}
```

---

### 🖼️ `img12` — Step 1: Define Multiple Type-Safe Tools

#### 🏷️ Slide Metadata
- **Slide ID:** `img12`
- **Slide Type:** Implementation: Tool Construction
- **Headline:** Step 1: Define Multiple Type-Safe Tools.
- **Sub-headline:** Pure Kotlin lambdas with auto-generated schemas, parameter types, and human confirmation gates.

#### 📝 Text Content
```text
Tools represent the actionable capabilities of your agents. In Aivo SDK, tools are pure Kotlin lambdas with auto-generated JSON schemas.

We build two distinct tools:
1. fetch_order: Safe, read-only lookup tool that queries order status and items.
2. process_refund: High-risk financial transaction tool marked with ToolRisk.HIGH and requiresConfirmation(true) to trigger human-in-the-loop authorization.
```

#### 💻 Code Snippet
```kotlin
// 1. Safe Read-Only Tool: Lookup order details
val fetchOrderTool = tool("fetch_order", "Fetch order status & history") {
    parameters {
        string("orderId", description = "Target order ID", required = true)
    }
    execute { call, _ ->
        val id = call.arguments["orderId"]?.jsonPrimitive?.content ?: ""
        ToolResult.Success(buildJsonObject {
            put("orderId", id)
            put("status", "DAMAGED_IN_TRANSIT")
            put("amount", 149.99)
        })
    }
}

// 2. High-Risk Action Tool: Issue financial refund
val processRefundTool = tool("process_refund", "Issue customer refund") {
    risk(ToolRisk.HIGH)
    requiresConfirmation(true) // Requires human approval before running
    parameters {
        string("orderId", description = "Order to refund", required = true)
        number("amount",  description = "Refund amount in USD", required = true)
    }
    execute { call, _ ->
        ToolResult.Success(buildJsonObject {
            put("status", "REFUND_PROCESSED")
            put("transactionId", "TX_78491")
        })
    }
}
```

#### 🎨 Visual Design Notes
- **Left Badge:** `Read-Only` (Green `#10B981`) for query tools.
- **Right Badge:** `High-Risk Action` (Amber `#F59E0B`) with human shield icon.
- **Highlight:** Schema auto-generation and type-safe argument unpacking.

```json
{
  "id": "img12",
  "tag": "STEP 1: DEFINE TOOLS",
  "title": "Step 1: Define Multiple Type-Safe Tools",
  "subtitle": "Declare Kotlin tools with auto-generated schemas, parameter types, and security risk gates.",
  "fileName": "SupportTools.kt",
  "lang": "kotlin",
  "swipeText": "Step 2: Define Agents ➔",
  "code": "// 1. Safe Read-Only Tool: Lookup order\nval fetchOrderTool = tool(\"fetch_order\", \"Get order status\") {\n    parameters { string(\"orderId\", required = true) }\n    execute { call, _ ->\n        ToolResult.Success(buildJsonObject { put(\"status\", \"DAMAGED\"); put(\"amount\", 149.99) })\n    }\n}\n\n// 2. High-Risk Tool: Issue refund (Human approval gate)\nval processRefundTool = tool(\"process_refund\", \"Refund payment\") {\n    risk(ToolRisk.HIGH)\n    requiresConfirmation(true)\n    parameters { string(\"orderId\", required = true); number(\"amount\", required = true) }\n    execute { call, _ ->\n        ToolResult.Success(buildJsonObject { put(\"status\", \"REFUNDED\"); put(\"txId\", \"TX_78491\") })\n    }\n}"
}
```

---

### 🖼️ `img13` — Step 2: Define Specialist Agents & Supervisor

#### 🏷️ Slide Metadata
- **Slide ID:** `img13`
- **Slide Type:** Implementation: Agent Configuration
- **Headline:** Step 2: Define Specialists & Supervisor Delegation.
- **Sub-headline:** Scope tools strictly to specialist agents and grant the Supervisor dynamic delegation authority.

#### 📝 Text Content
```text
Don't dump all tools into one overloaded agent prompt. LLMs perform far better when each agent has a single, well-defined role.

We create three focused agents:
1. orders_agent: Only knows how to inspect orders (tools("fetch_order")).
2. finance_agent: Only knows how to issue refunds (tools("process_refund")).
3. supervisor: Directs customer requests. Instead of raw tools, it uses delegates("orders_agent", "finance_agent").

Aivo SDK automatically manufactures dynamic delegate_to_* tools so the supervisor can invoke sub-agents seamlessly!
```

#### 💻 Code Snippet
```kotlin
val aivo = AivoSdk {
    agents {
        // Specialist 1: Inspects order state and delivery records
        define("orders_agent") {
            name = "Orders Specialist"
            systemPrompt = "You inspect order states, item damage reports, and return windows."
            tools("fetch_order")
        }

        // Specialist 2: Handles financial ledger operations
        define("finance_agent") {
            name = "Finance Specialist"
            systemPrompt = "You process approved refunds and generate payment reversals."
            tools("process_refund")
        }

        // Supervisor: Lead orchestrator coordinates the squad
        define("supervisor") {
            name = "Support Supervisor"
            systemPrompt = """
                You coordinate customer support resolutions:
                1. Delegate to 'orders_agent' to verify order status.
                2. If damaged, delegate to 'finance_agent' to issue refund.
                3. Formulate the final reassuring resolution message.
            """.trimIndent()
            delegates("orders_agent", "finance_agent")
        }
    }
}
```

#### 🎨 Visual Design Notes
- **Contrast Pillars:** Show the boundary separation between `Orders Specialist` and `Finance Specialist`.
- **Annotation Arrow:** Highlight `delegates("orders_agent", "finance_agent")` pointing to auto-synthesized tools.
- **Accent Color:** Cyber Purple (`#8B5CF6`).

```json
{
  "id": "img13",
  "tag": "STEP 2: DEFINE AGENTS",
  "title": "Step 2: Define Specialists & Supervisor",
  "subtitle": "Scope tools to specialist agents and give the Supervisor dynamic delegation authority.",
  "fileName": "AgentSquad.kt",
  "lang": "kotlin",
  "swipeText": "Step 3: Connect Cycle ➔",
  "code": "agents {\n    // Specialist 1: Order verification\n    define(\"orders_agent\") {\n        systemPrompt = \"Verify order status and return eligibility.\"\n        tools(\"fetch_order\")\n    }\n\n    // Specialist 2: Financial operations\n    define(\"finance_agent\") {\n        systemPrompt = \"Execute approved payment refunds.\"\n        tools(\"process_refund\")\n    }\n\n    // Supervisor: Coordinates the squad\n    define(\"supervisor\") {\n        systemPrompt = \"Delegate lookup to orders_agent, then refund to finance_agent.\"\n        delegates(\"orders_agent\", \"finance_agent\")\n    }\n}"
}
```

---

### 🖼️ `img14` — Step 3: Connect All into One Autonomous Cycle

#### 🏷️ Slide Metadata
- **Slide ID:** `img14`
- **Slide Type:** Implementation: Engine Assembly
- **Headline:** Step 3: Connect All into One Unified Cycle.
- **Sub-headline:** Wire model provider, multiple tools, agents, entry agent, and security gates in one unified builder.

#### 📝 Text Content
```text
Connecting multiple tools and multiple agents takes just one clean configuration block in Aivo SDK.

The single builder wires together:
✔ LLM Provider: Google Gemini 3.8 Flash (or Ollama/OpenRouter)
✔ Tool Registry: Registers fetchOrderTool and processRefundTool
✔ Agent Squad: Registers orders_agent, finance_agent, and supervisor
✔ Entry Agent: Routes the conversation entry to supervisor
✔ Security Gate: Human-in-the-loop callback for high-risk tools

Everything runs together in one unified state machine.
```

#### 💻 Code Snippet
```kotlin
val aivo = AivoSdk {
    // 1. LLM Provider (Google Gemini Flash)
    providers {
        gemini("gemini") { apiKey(BuildConfig.GEMINI_API_KEY) }
    }
    defaultModel(GeminiModel.GEMINI_3_8_FLASH)

    // 2. Register all tools
    tools {
        register(fetchOrderTool)
        register(processRefundTool)
    }

    // 3. Register agent team and designate entry agent
    agents {
        define("orders_agent")  { tools("fetch_order") }
        define("finance_agent") { tools("process_refund") }
        define("supervisor")    { delegates("orders_agent", "finance_agent") }
    }
    entryAgent("supervisor") // Entry point of the cycle!

    // 4. Human-in-the-loop security confirmation gate
    security {
        confirmationHandler = { call, spec ->
            confirmDialog("Approve ${spec.name} for ${call.arguments}?")
        }
    }
}
```

#### 🎨 Visual Design Notes
- **Central Box:** A glowing blueprint container labeled `AivoSdk { ... }` holding all components.
- **Checkmarks:** Visual indicators confirming Provider, Tools, Agents, Entry Agent, and Security Gate are all active.
- **Accent Color:** Emerald Green (`#10B981`) denoting complete integration.

```json
{
  "id": "img14",
  "tag": "STEP 3: CONNECT CYCLE",
  "title": "Step 3: Connect All into One Unified Cycle",
  "subtitle": "Wire providers, multiple tools, agents, entry point, and security gates into one builder.",
  "fileName": "SupportEngine.kt",
  "lang": "kotlin",
  "swipeText": "Step 4: Handle Loop ➔",
  "code": "val aivo = AivoSdk {\n    providers { gemini(\"gemini\") { apiKey(BuildConfig.GEMINI_API_KEY) } }\n    defaultModel(GeminiModel.GEMINI_3_8_FLASH)\n\n    tools {\n        register(fetchOrderTool)\n        register(processRefundTool)\n    }\n\n    agents {\n        define(\"orders_agent\")  { tools(\"fetch_order\") }\n        define(\"finance_agent\") { tools(\"process_refund\") }\n        define(\"supervisor\")    { delegates(\"orders_agent\", \"finance_agent\") }\n    }\n    entryAgent(\"supervisor\")\n\n    security {\n        confirmationHandler = { call, spec -> confirmDialog(\"Approve ${spec.name}?\") }\n    }\n}"
}
```

---

### 🖼️ `img15` — Step 4: Execute & Handle the Full Agents Loop

#### 🏷️ Slide Metadata
- **Slide ID:** `img16`
- **Slide Type:** Implementation: Runtime & Turn Lifecycle
- **Headline:** Step 4: Execute & Handle the Full Agents Loop.
- **Sub-headline:** Trigger the loop with 1 method and observe delegations, tool dispatches, and tokens via Kotlin Flow.

#### 📝 Text Content
```text
Calling aivo.stream(...) triggers the entire multi-agent cycle autonomously.

The runtime drives the entire loop under the hood:
1. supervisor analyzes request ➔ Decides to delegate.
2. Emits AgentEntered("orders_agent").
3. orders_agent runs fetch_order ➔ Emits ToolCallStarted & ToolCallExecuted.
4. Output returns to supervisor ➔ Evaluates refund eligibility.
5. Emits AgentEntered("finance_agent") ➔ process_refund tool invoked.
6. Security gate prompts confirmation ➔ Action approved.
7. supervisor synthesizes results and streams final tokens to UI!
```

#### 💻 Code Snippet
```kotlin
// Stream the complete multi-agent cycle in real time
aivo.stream(
    conversationId = ConversationId("ticket-402"),
    prompt = "Order #1082 arrived broken. Please inspect and refund."
).collect { event ->
    when (event) {
        // Track agent transitions
        is AgentEvent.AgentEntered     -> println("🔀 Active Agent: ${event.agentId}")
        
        // Track tool executions inside child specialists
        is AgentEvent.ToolCallStarted  -> println("⚙️ Tool Invoked: ${event.toolName}")
        is AgentEvent.ToolCallExecuted -> println("✅ Tool Finished: ${event.toolName}")
        
        // Stream live text tokens to UI (Compose / SwiftUI)
        is AgentEvent.TextDelta        -> print(event.text)
        
        // Handle cycle completion
        is AgentEvent.RunCompleted     -> println("\n🎉 Autonomous cycle complete!")
        is AgentEvent.RunFailed        -> println("❌ Error: ${event.error.message}")
        else -> Unit
    }
}
```

#### 🎨 Visual Design Notes
- **Flow Timeline:** Vertical event timeline showing step-by-step progress from prompt to delegation, tool call, confirmation, and final response.
- **Terminal View:** Clean simulated CLI output previewing colored event logs.
- **Accent Color:** Neon Cyan (`#06B6D4`).

```json
{
  "id": "img15",
  "tag": "STEP 4: HANDLE LOOP",
  "title": "Step 4: Execute & Handle the Full Agents Loop",
  "subtitle": "Trigger the entire squad with 1 call and stream delegations, tool calls, and text in real time.",
  "fileName": "RunLoop.kt",
  "lang": "kotlin",
  "swipeText": "See Safeguards ➔",
  "code": "// Stream the multi-agent cycle with reactive Kotlin Flow\naivo.stream(ConversationId(\"ticket-402\"), \"Order #1082 arrived broken. Refund please.\")\n    .collect { event ->\n        when (event) {\n            is AgentEvent.AgentEntered     -> println(\"🔀 Active Agent: ${event.agentId}\")\n            is AgentEvent.ToolCallStarted  -> println(\"⚙️ Tool Called: ${event.toolName}\")\n            is AgentEvent.ToolCallExecuted -> println(\"✅ Tool Finished: ${event.toolName}\")\n            is AgentEvent.TextDelta        -> print(event.text) // Live tokens\n            is AgentEvent.RunCompleted     -> println(\"🎉 Cycle Complete!\")\n            is AgentEvent.RunFailed        -> println(\"❌ Error: ${event.error.message}\")\n            else -> Unit\n        }\n    }"
}
```

---

### 🖼️ `img16` — Step 5: Built-in Loop Safeguards & Cycle Detection

#### 🏷️ Slide Metadata
- **Slide ID:** `img16`
- **Slide Type:** Architecture: Production Reliability
- **Headline:** Step 5: Built-in Guards Stop Infinite Loops.
- **Sub-headline:** Built-in cycle detection, isolated child contexts, and step ceilings keep squads bulletproof.

#### 📝 Text Content
```text
What prevents a supervisor and sub-agent from delegating back and forth forever?
What stops large tool JSON payloads from blowing up context token limits?

Aivo SDK has production safeguards baked directly into the engine:
1. Cycle Detection: Catches circular delegation (A ➔ B ➔ A) and halts immediately with DelegationCycleException.
2. Max Delegation Depth: Enforces strict nesting limits (default: 3 levels).
3. Context Isolation: Child agents run in isolated sub-scopes — massive database payloads never pollute the supervisor's prompt window.
4. Step & Timeout Caps: Prevents runaway tool execution or budget drains.
```

#### 💻 Code Snippet
```kotlin
val aivo = AivoSdk {
    // 1. Strict loop execution ceilings
    runtime {
        maxSteps = 12             // Maximum tool reasoning turns
        maxDelegationDepth = 3    // Maximum supervisor nesting depth
        parallelToolExecution = true
        toolTimeoutMs = 15_000L   // Timeout per tool execution
    }

    // 2. Sliding window memory bounds
    memory {
        slidingWindow(maxMessages = 20) // Automatically evicts stale turns
    }
}

// Built-in Cycle Detector in Action:
// If 'orders_agent' tries to delegate back to 'supervisor',
// Aivo halts immediately: throws DelegationCycleException!
```

#### 🎨 Visual Design Notes
- **Shield Graphic:** Golden shield icon featuring anti-recursion protection.
- **Formula Box:** `Supervisor ➔ SubAgent ➔ Supervisor = DelegationCycleException (Blocked)`.
- **Accent Color:** Crimson Amber (`#F59E0B`).

```json
{
  "id": "img16",
  "tag": "LOOP SAFEGUARDS",
  "title": "Step 5: Built-in Guards Stop Infinite Loops",
  "subtitle": "Built-in cycle detection, context isolation, and step bounds keep agent squads bulletproof.",
  "fileName": "LoopGuards.kt",
  "lang": "kotlin",
  "swipeText": "See Full Blueprint ➔",
  "code": "val aivo = AivoSdk {\n    // 1. Strict execution ceilings\n    runtime {\n        maxSteps = 12             // Max tool turns per session\n        maxDelegationDepth = 3    // Hard recursion ceiling\n        toolTimeoutMs = 15_000L   // Tool execution timeout\n    }\n\n    // 2. Context window bounds\n    memory {\n        slidingWindow(maxMessages = 20) // Auto-evicts stale turns\n    }\n}\n\n// Runtime Auto-Guard:\n// A -> B -> A throws DelegationCycleException (Zero infinite loops!)"
}
```

---

### 🖼️ `img17` — The Complete Multi-Agent Stack (Production Finale)

#### 🏷️ Slide Metadata
- **Slide ID:** `img17`
- **Slide Type:** Masterclass Summary & CTA
- **Headline:** Build Autonomous Agent Squads in Kotlin.
- **Sub-headline:** Multiple tools. Multiple agents. One unified cycle. Zero boilerplate.

#### 📝 Text Content
```text
You don't need brittle while loops, manual JSON parsers, or bloated frameworks to build production agent teams.

With Aivo SDK:
✔ Type-safe tools with auto-generated schemas & confirmation gates
✔ Supervisor orchestration with automated cycle detection
✔ Reactive Flow streaming for Jetpack Compose & SwiftUI
✔ True cross-platform: Runs natively on Android, JVM, and iOS

Add the dependency and start orchestrating autonomous agent loops today!
```

#### 💻 Code Snippet
```kotlin
// Android, JVM & iOS commonMain:
dependencies {
    implementation("io.github.abdallah-elsobky:aivo-sdk:1.0.1")
}

// 📦 Maven Central: io.github.abdallah-elsobky:aivo-sdk:1.0.1
// 🌐 Interactive Docs: abdallah-elsobky.github.io/Aivo-SDK/
// ⭐ GitHub: github.com/Abdallah-Elsobky/Aivo-SDK
```

#### 🎨 Visual Design Notes
- **Hero Card:** Clean celebratory design with Maven coordinate and copy-to-clipboard badge.
- **Action Buttons:** `⭐ Star on GitHub` | `📖 Explore Interactive Docs` | `💬 Share Thoughts`.
- **Author Signoff:** Built by Abdallah Elsobky.

```json
{
  "id": "img17",
  "tag": "SHIP AUTONOMOUS AGENTS",
  "title": "Build Autonomous Agent Squads in Kotlin",
  "subtitle": "Multiple tools. Multiple agents. One unified cycle. Zero boilerplate. Available now on Maven Central.",
  "fileName": "build.gradle.kts",
  "lang": "kotlin",
  "swipeText": "Star on GitHub ⭐",
  "code": "// One dependency for autonomous agent squads across Android, JVM & iOS:\nimplementation(\"io.github.abdallah-elsobky:aivo-sdk:1.0.1\")\n\n// 📦 Maven Central: io.github.abdallah-elsobky:aivo-sdk:1.0.1\n// 🌐 Interactive Docs: abdallah-elsobky.github.io/Aivo-SDK/\n// ⭐ GitHub: github.com/Abdallah-Elsobky/Aivo-SDK"
}
```

---

## 📊 Quick Extraction Matrix (All Slides at a Glance)

| Slide ID | Topic / Focus | Core Hook / Value Prop | Code Snippet Summary | Target Action |
|:---|:---|:---|:---|:---|
| **img1** | Stop Coding Loop by Hand | Calling LLM is easy, managing the loop is brutal | `AivoSdk.create(provider = ...)` | Stop scrolling & swipe |
| **img2** | 30-Sec Agent Cycle | Autonomous cycle with state retention in 3 lines | 4-line chat execution | Marvel at simplicity |
| **img3** | Cross-Platform Engine | Same agent loop runs in Android, JVM & iOS | `implementation("aivo-sdk:1.0.1")` | Copy dependency |
| **img4** | Model-Agnostic Execution | Swap models without touching agent logic | Multi-provider initialization | Realize zero lock-in |
| **img5** | Autonomous Tool Execution | Engine runs tools & feeds results into loop | `tool("get_weather") { ... }` | See function calling power |
| **img6** | Multi-Agent Squads | Supervisor delegation with loop cycle guards | `delegates("researcher", "writer")` | Explore supervisor model |
| **img7** | Reactive Thought Streaming | Stream live text, reasoning & tool statuses | `aivo.stream(...).collect { ... }` | Wire up reactive UI |
| **img8** | Declarative Blueprints | Tune prompts and maxSteps without recompiling | Markdown frontmatter + Kotlin | Prompt separation |
| **img9** | Production Guardrails | Sliding memory, retries & log protection | `slidingWindow(20)` + `SecretString` | Trust production readiness |
| **img10** | Deterministic Test Doubles | Verify multi-turn loops offline for $0 | `FakeLlmProvider.scripted(...)` | Appreciate testability |
| **img11** | Squad Architecture Map | Blueprint of supervisor, specialists & tools | ASCII Architecture Diagram | Understand the squad |
| **img12** | Step 1: Multiple Tools | Pure Kotlin tools with schemas & risk flags | `tool("fetch_order")` + `tool("process_refund")` | Build safe tools |
| **img13** | Step 2: Specialist Agents | Scope tools to agents & wire delegation | `delegates("orders_agent", "finance_agent")` | Define squad roles |
| **img14** | Step 3: Unified Cycle | Connect models, tools, agents & security gates | Unified `AivoSdk { ... }` builder | Assemble the engine |
| **img15** | Step 4: Handle Full Loop | Stream delegation transitions & live tokens | `aivo.stream(...).collect { ... }` | Stream complete cycle |
| **img16** | Step 5: Loop Safeguards | Stop infinite loops, recursion & token overflow | `maxDelegationDepth` + `slidingWindow` | Prevent loop deadlocks |
| **img17** | Complete Stack & Finale | Ship production autonomous squads in KMP | Maven Central + GitHub links | Star repo & build |

---

## 🎨 Recommended Figma / Canva / PostCraft Studio Guidelines

When rendering these slides for LinkedIn:
1. **Dimensions:** `1080 x 1350 px` (4:5 Aspect Ratio — optimal for LinkedIn mobile feed real estate).
2. **Typography:**
   - **Headlines:** *Plus Jakarta Sans* or *Outfit* (Bold, 52-64pt).
   - **Body Text:** *Inter* (Medium, 28-32pt).
   - **Code Font:** *JetBrains Mono* (SemiBold, 20-24pt, auto-fitted with syntax highlighting).
3. **Color Palette:**
   - Background: Dark Slate `#0A0F1D` with subtle radial glow `#1E1B4B`.
   - Card Containers: Glassmorphism `#161F38` with `1px` stroke `#2A3B66` and `16px` border-radius.
   - Primary Accent: Neon Violet `#7C3AED`.
   - Secondary Accent: Cyber Mint `#10B981`.
   - Highlight Tag: Amber Glow `#F59E0B`.
4. **Slide Navigation:** Include a top progress bar or bottom slide counter (`01 / 17`, `02 / 17`, etc.) so readers swipe through to the end.
