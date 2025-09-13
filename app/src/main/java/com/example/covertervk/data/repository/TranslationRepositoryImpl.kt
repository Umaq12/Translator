package com.example.covertervk.data.repository

import com.example.covertervk.data.local.TranslationDao
import com.example.covertervk.data.local.TranslationEntity
import com.example.covertervk.data.remote.ExchangeApi
import com.example.covertervk.domain.model.Translation
import com.example.covertervk.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val api: ExchangeApi,
    private val dao: TranslationDao
) : TranslationRepository {

    override suspend fun translateAndSave(word: String): Translation {
        val list = api.searchWord(word, pageSize = 1)
        val dto = list.firstOrNull()
            ?: throw NoSuchElementException("No translation for \"$word\"")

        val meaning = dto.meanings.firstOrNull()
        val translated = meaning?.translation?.text ?: "—"
        val transcription = meaning?.transcription

        val entity = TranslationEntity(
            source = dto.text,
            translated = translated,
            transcription = transcription
        )
        val id = dao.insert(entity)
        return entity.copy(id = id).toDomain()
    }

    override fun getHistory(): Flow<List<Translation>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun getFavorites(): Flow<List<Translation>> =
        dao.observeFavorites().map { list -> list.map { it.toDomain() } }

    override suspend fun toggleFavorite(id: Long) {
        dao.toggleFavorite(id)
    }

    override suspend fun deleteFromHistory(id: Long) {
        dao.deleteById(id)
    }
}

/* Мапперы */
private fun TranslationEntity.toDomain(): Translation =
    Translation(
        id = id,
        text = source,
        translation = translated,
        transcription = transcription,
        isFavorite = isFavorite
    )

private fun Translation.toEntity(): TranslationEntity =
    TranslationEntity(
        id = id ?: 0,
        source = text,
        translated = translation,
        transcription = transcription,
        isFavorite = isFavorite
    )


