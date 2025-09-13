package com.example.covertervk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covertervk.domain.model.Translation
import com.example.covertervk.domain.use_case.*
import com.example.covertervk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val translateWordUseCase: TranslateWordUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val deleteFromHistoryUseCase: DeleteFromHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TranslationState())
    val state: StateFlow<TranslationState> = _state.asStateFlow()

    init {
        observeHistory()
        observeFavorites()
    }

    fun translate(word: String) {
        viewModelScope.launch {
            if (word.isBlank()) {
                _state.value = _state.value.copy(error = "Введите слово")
                return@launch
            }
            translateWordUseCase(word).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = "")
                    is Resource.Success -> _state.value = _state.value.copy(isLoading = false, error = "")
                    is Resource.Error -> _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Ошибка")
                }
            }
        }
    }

    private fun observeHistory() {
        viewModelScope.launch {
            getHistoryUseCase().collectLatest { history ->
                _state.value = _state.value.copy(history = history)
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { favorites ->
                _state.value = _state.value.copy(favorites = favorites)
            }
        }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch { toggleFavoriteUseCase(id) }
    }

    fun deleteFromHistory(id: Long) {
        viewModelScope.launch { deleteFromHistoryUseCase(id) }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
}
