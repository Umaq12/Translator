package com.example.covertervk.domain.use_case

import com.example.covertervk.domain.model.Translation
import com.example.covertervk.domain.repository.TranslationRepository
import com.example.covertervk.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TranslateWordUseCase @Inject constructor(
    private val repository: TranslationRepository
) {
    operator fun invoke(word: String): Flow<Resource<Translation>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.translateAndSave(word)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Server error"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
