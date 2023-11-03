package com.lighthouse.multi_module_navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighthouse.domain.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {
    private val _activate = MutableLiveData<Boolean>()
    val activate: LiveData<Boolean> get() = _activate

    init {
        viewModelScope.launch {
            _activate.value = repository.fetchRemoteConfig()
        }
    }
}