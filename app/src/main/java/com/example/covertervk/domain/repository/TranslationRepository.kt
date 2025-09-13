package com.example.covertervk.domain.repository

import com.example.covertervk.data.remote.dto.WordDto
import com.example.covertervk.domain.model.Translation
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    suspend fun translateAndSave(word: String): Translation
    fun getHistory(): Flow<List<Translation>>
    fun getFavorites(): Flow<List<Translation>>
    suspend fun toggleFavorite(id: Long)
    suspend fun deleteFromHistory(id: Long)
}

