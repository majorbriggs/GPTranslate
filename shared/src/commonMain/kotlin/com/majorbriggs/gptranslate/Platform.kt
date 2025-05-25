package com.majorbriggs.gptranslate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform