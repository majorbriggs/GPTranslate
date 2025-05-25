package com.majorbriggs.gptranslate.shared.network

import com.majorbriggs.gptranslate.shared.entity.Translation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationResponse(
    @SerialName("id") val id: String,
    @SerialName("created") val created: Long,
    @SerialName("choices") val choices: List<Choice>,
) {

    fun toTranslation(query: String) =
        Translation(
            id = id,
            query = query,
            message = choices.firstOrNull()?.message?.content ?: "EMPTY",
            created = created,
        )

    @Serializable
    data class Choice(
        @SerialName("message") val message: Message,
    ) {

        @Serializable
        data class Message(
            @SerialName("content") val content: String,
        )
    }
}
