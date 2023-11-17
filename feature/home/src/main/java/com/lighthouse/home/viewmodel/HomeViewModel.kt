package com.lighthouse.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lighthouse.common_ui.base.BaseViewModel
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuestionRepository
) : BaseViewModel() {
    private val _result = MutableStateFlow<UiState>(UiState.Loading)
    val result: StateFlow<UiState> = _result.asStateFlow()

    fun getQuestionList(pageSize: Int?) {
        viewModelScope.launch {
            repository.getQuestionList(pageSize)
                .catch {
                    Log.e("TESTING", it.stackTraceToString())
                }
                .collect { questions ->
                    questions.fold(
                        onSuccess = {
                            _result.emit(UiState.Success(it))
                        },
                        onFailure = {
                            Log.d("TESTING", it.stackTraceToString())
                        }
                    )
                }
        }
    }

    fun getQuestionContent(questionId: String?) {
        viewModelScope.launch {
            repository.getQuestionContent(questionId)
                .catch {
                    _result.emit(UiState.Error(it.message ?: "Error"))
                }
                .collect { content ->
                    content.fold(
                        onSuccess = {
                            _result.emit(UiState.Success(it))
                        },
                        onFailure = {
                            _result.emit(UiState.Error(it.stackTraceToString()))
                        }
                    )

                }
        }
    }

    fun getTest() {
        viewModelScope.launch {
            repository.getTest()
                .catch {
                    _result.value = handleException(it)
                }
                .collect { content ->
                    _result.emit(UiState.Success(content))
                }
        }
    }

    fun <T> save(key: String, value: T, isEncrypted: Boolean) =
        repository.save(key, value, isEncrypted)

    fun <T> getValue(key: String, defaultValue: T, isEncrypted: Boolean): T =
        repository.getValue(key, defaultValue, isEncrypted)
}