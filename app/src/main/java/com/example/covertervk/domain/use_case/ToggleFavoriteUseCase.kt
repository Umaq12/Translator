package com.example.covertervk.domain.use_case

import com.example.covertervk.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: TranslationRepository
) {
    suspend operator fun invoke(id: Long) = repository.toggleFavorite(id)
}
