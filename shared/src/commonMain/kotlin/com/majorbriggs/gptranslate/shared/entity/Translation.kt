package com.majorbriggs.gptranslate.shared.entity

data class Translation(
    val id: String,
    val query: String,
    val message: String,
    val created: Long,
)
