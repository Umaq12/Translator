package com.example.covertervk.presentation

import com.example.covertervk.domain.model.Translation

data class TranslationState(
    val isLoading: Boolean = false,
    val history: List<Translation> = emptyList(),
    val favorites: List<Translation> = emptyList(),
    val error: String = ""
)
