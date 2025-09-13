package com.example.covertervk.data.remote.dto

import com.example.covertervk.domain.model.Translation

data class WordDto(
    val id: Long,
    val text: String,
    val meanings: List<MeaningDto>
)

data class MeaningDto(
    val id: Long,
    val partOfSpeechCode: String?,
    val translation: TranslationDto?,
    val previewUrl: String?,
    val imageUrl: String?,
    val transcription: String?,
    val soundUrl: String?
)

data class TranslationDto(
    val text: String?,
    val note: String?
)

/**
 * Маппер для доменной модели
 * Берём только один перевод (первый в meanings).
 */


fun WordDto.toDomain(): Translation {
    val meaning = meanings.firstOrNull()
    return Translation(
        text = text,
        translation = meaning?.translation?.text ?: "—",
        transcription = meaning?.transcription
    )
}

