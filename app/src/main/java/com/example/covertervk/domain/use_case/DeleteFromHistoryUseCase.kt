package com.example.covertervk.domain.use_case

import com.example.covertervk.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFromHistoryUseCase @Inject constructor(
    private val repo: TranslationRepository
) {
    suspend operator fun invoke(id: Long) = repo.deleteFromHistory(id)
}
