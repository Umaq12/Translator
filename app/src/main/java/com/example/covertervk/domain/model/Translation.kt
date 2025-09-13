package com.example.covertervk.domain.model

data class Translation(
    val id: Long? = null,              // id из Room
    val text: String,                  // слово на английском
    val translation: String,           // перевод на русском
    val transcription: String? = null, // транскрипция
    val isFavorite: Boolean = false    // избранное
)
