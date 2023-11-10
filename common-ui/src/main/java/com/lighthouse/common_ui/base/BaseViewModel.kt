package com.lighthouse.common_ui.base

import androidx.lifecycle.ViewModel
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.domain.constant.TestException

open class BaseViewModel : ViewModel() {
    protected fun handleException(e: Throwable): UiState.Error<*> {
        return when (e) {
            is TestException -> UiState.Error(e)
            else -> UiState.Error("Error")
        }
    }
}