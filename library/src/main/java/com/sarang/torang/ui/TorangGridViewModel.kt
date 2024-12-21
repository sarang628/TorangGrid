package com.sarang.torang.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedGridUiState {
    data class Error(val msg: String?) : FeedGridUiState
    data class Success(val list: List<String?>) :
        FeedGridUiState

    object Loading : FeedGridUiState
}

@HiltViewModel
class TorangGridViewModel @Inject constructor(
    private val useCase: GetFeedGridUseCase
) : ViewModel() {
    var uiState: FeedGridUiState by mutableStateOf(FeedGridUiState.Loading)

    init {
        viewModelScope.launch {
            useCase.invoke().collect {
                uiState = FeedGridUiState.Success(it)
            }
        }
    }
}