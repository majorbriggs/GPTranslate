package com.majorbriggs.gptranslate.shared.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class OpenAIApi {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    init {
        httpClient.plugin(HttpSend).intercept { request ->
            val call = execute(request)
            val response = call.response
            val durationMillis = response.responseTime.timestamp - response.requestTime.timestamp
            println("DUPA: [${response.status.value}] ${request.url.build()} ($durationMillis ms)")
            call
        }
    }

    private val apiKey = "" // TODO: Replace with your OpenAI API key
    suspend fun getResponse(word: String): TranslationResponse =
        httpClient.post("https://api.openai.com/v1/chat/completions") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, "Bearer $apiKey")
            }
            setBody(
                OpenAIRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(
                            role = "system",
                            content = "You are a translator."
                        ),
                        Message(
                            role = "user",
                            content = "Translate this word \"$word\" into English (if it's not already english word), Spanish, German and French. Give 2 example sentences in each language. Keep the answer short."
                        )
                    ),
                    temperature = 1,
                    maxTokens = 256,
                    topP = 1,
                    frequencyPenalty = 0,
                    presencePenalty = 0
                )
            )
        }.body()
}
