package com.lighthouse.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.usecase.QuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: QuestionUseCase,
) : ViewModel() {
    private val _result = MutableStateFlow<UiState>(UiState.Loading)
    val result: StateFlow<UiState> = _result.asStateFlow()

    fun getQuestionList(pageSize: Int?) {
        viewModelScope.launch {
            useCase.getQuestionList(pageSize)
                .catch {
                    _result.emit(UiState.Error(it.message ?: "Error"))
                }
                .collect { questions ->
                    questions.fold(
                        onSuccess = {
                            _result.emit(UiState.Success(it))
                        },
                        onFailure = {
                            _result.emit(UiState.Error(it.message ?: "error"))
                        }
                    )
                }
        }
    }

    fun getQuestionContent(questionId: String?) {
        viewModelScope.launch {
            useCase.getQuestionContent(questionId)
                .catch {
                    _result.emit(UiState.Error(it.message ?: "Error"))
                }
                .collect { content ->
                    content.fold(
                        onSuccess = {
                            _result.emit(UiState.Success(it))
                        },
                        onFailure = {
                            _result.emit(UiState.Error(it.message ?: "error"))
                        }
                    )

                }
        }
    }
}