package com.majorbriggs.gptranslate.shared

import com.majorbriggs.gptranslate.shared.cache.Database
import com.majorbriggs.gptranslate.shared.cache.DatabaseDriverFactory
import com.majorbriggs.gptranslate.shared.entity.Translation
import com.majorbriggs.gptranslate.shared.network.OpenAIApi

class GPTranslateSDK(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = Database(databaseDriverFactory)
    private val api = OpenAIApi()

    @Throws(Exception::class)
    suspend fun getTranslations(query: String, forceReload: Boolean): List<Translation> {
        val cachedLaunches = database.getAllTranslations()
        return if (cachedLaunches.isNotEmpty() && !forceReload || query.isBlank()) {
            cachedLaunches
        } else {
            api.getResponse(query).toTranslation(query).also {
                database.insertTranslation(it)
            }
            database.getAllTranslations()
        }
    }

    @Throws(Exception::class)
    fun deleteTranslation(id: String) {
        database.deleteTranslation(id)
    }
}
