package com.majorbriggs.gptranslate.shared.cache

import com.majorbriggs.gptranslate.shared.entity.Translation

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllTranslations()
        }
    }

    internal fun getAllTranslations(): List<Translation> {
        return dbQuery.selectAllTranslationsInfo(::mapTranslation).executeAsList()
    }

    internal fun deleteTranslation(id: String) {
        dbQuery.transaction {
            dbQuery.deleteTranslation(id)
        }
    }

    private fun mapTranslation(
        id: String,
        query: String,
        message: String,
        created: Long,
    ): Translation {
        return Translation(
            id = id, created = created, query = query, message = message,
        )
    }

    fun insertTranslation(translation: Translation) {
        dbQuery.insertTranslation(
            id = translation.id,
            query = translation.query,
            answer = translation.message,
            created = translation.created,
        )
    }
}
