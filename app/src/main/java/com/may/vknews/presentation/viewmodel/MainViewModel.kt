package com.may.vknews.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.vknews.data.repository.NewsFeedRepositoryImpl
import com.may.vknews.domain.usecases.CheckAuthStateUseCase
import com.may.vknews.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateUseCase,
    private val checkAuthStatusUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateFlowUseCase()

    fun onSuccess() {
        viewModelScope.launch {
            checkAuthStatusUseCase()
        }
    }

    fun onFail(fail: String) {
        viewModelScope.launch {
            checkAuthStatusUseCase()
        }
    }
}