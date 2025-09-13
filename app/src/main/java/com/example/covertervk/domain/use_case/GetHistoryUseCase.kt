package com.example.covertervk.domain.use_case

import com.example.covertervk.domain.model.Translation
import com.example.covertervk.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repo: TranslationRepository
) {
    operator fun invoke() = repo.getHistory()
}
