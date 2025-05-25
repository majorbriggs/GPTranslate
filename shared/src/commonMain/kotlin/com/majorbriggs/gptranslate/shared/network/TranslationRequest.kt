package com.majorbriggs.gptranslate.shared.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpenAIRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<Message>,
    @SerialName("temperature") val temperature: Int,
    @SerialName("max_tokens") val maxTokens: Int,
    @SerialName("top_p") val topP: Int,
    @SerialName("frequency_penalty") val frequencyPenalty: Int,
    @SerialName("presence_penalty") val presencePenalty: Int
)

@Serializable
data class Message(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String
)
