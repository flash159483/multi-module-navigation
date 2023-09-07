package com.lighthouse.common_ui.util

sealed class UiState {
    object Loading : UiState()

    data class Success<R : Any>(val data: R) : UiState()

    data class Error(val message: String) : UiState()
}