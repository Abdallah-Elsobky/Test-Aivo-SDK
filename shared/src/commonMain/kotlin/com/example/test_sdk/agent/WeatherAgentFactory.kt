package com.example.test_sdk.agent

import com.aivo.sdk.AivoSdk
import com.aivo.sdk.provider.ollama.OllamaModel
import com.example.test_sdk.config.BuildConfig
import com.example.test_sdk.data.network.WeatherRemoteService

object WeatherAgentFactory {

    const val AGENT_ID = "weather_assistant"

    /**
     * Creates an [AivoSdk] instance configured exclusively with the Ollama provider
     * and managing the weather assistant agent with its two live tools.
     */
    fun create(
        apiKey: String = BuildConfig.OLLAMA_API_KEY,
        modelName: String = OllamaModel.GPT_OSS_120B.modelId,
        service: WeatherRemoteService = WeatherRemoteService(),
    ): AivoSdk {
        print("roto $apiKey")
        val coordsTool = WeatherTools.createCountryCoordinatesTool(service)
        val weatherTool = WeatherTools.createWeatherConditionsTool(service)

        return AivoSdk {
            providers {
                ollama("ollama") {
                    if (apiKey.isNotBlank()) {
                        apiKey(apiKey)
                    }
                }
            }

            defaultModel("ollama:$modelName")

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
                    
                        Language and Tone Rules:
                        1. Always respond in the same language used by the user in their latest message.
                        2. If the user writes in Arabic, respond in Arabic. If the user writes in English, respond in English.
                        3. If the user uses another language, respond in that language whenever possible.
                        4. Match the user's communication style and tone, including formal, casual, friendly, or professional language.
                        5. If the user uses a dialect, such as Egyptian Arabic, naturally use the same dialect when appropriate.
                        6. Do not switch languages unnecessarily or translate the response into another language unless explicitly requested.
                        7. Keep weather terminology natural and easy to understand for the user.
                        8. Always prioritize clarity, accuracy, and a friendly, respectful tone.
                    
                        Response Formatting Rules:
                        1. Use plain, readable text only. Never use Markdown formatting.
                        2. Never wrap text in asterisks, including bold text such as **Temperature** or italic text such as *Temperature*.
                        3. Do not use special characters for decoration, including asterisks, carets, backticks, hashtags, or other unreadable symbols.
                        4. Do not use Markdown headings, bullet symbols, tables, code blocks, or decorative separators.
                        5. Structure the response using clear labels and separate lines to make it visually organized and easy to read.
                        6. Use simple punctuation only when necessary for readability, such as commas, periods, colons, and parentheses.
                        7. Use normal text labels followed by their values. Keep each piece of information on its own line.
                        8. Preserve meaningful content such as country flag emojis, temperature units, coordinate values, and percentage signs when necessary.
                        9. Never output raw JSON, XML, tool calls, escape sequences, or other machine-readable formatting in the final response.
                        10. Before responding, verify that the final text follows all formatting rules and contains no unwanted Markdown syntax or decorative characters.
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
