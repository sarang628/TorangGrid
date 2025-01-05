package com.sarang.torang.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedGridUiState {
    data class Error(val msg: String?) : FeedGridUiState
    data class Success(val list: List<Pair<Int, String?>>, val isRefreshing: Boolean) :
        FeedGridUiState

    object Loading : FeedGridUiState
}

@HiltViewModel
class TorangGridViewModel @Inject constructor(
    private val useCase: GetFeedGridUseCase,
    private val loadFeedUserCase: LoadFeedUseCase,
    private val refreshFeedUseCase: RefreshFeedUseCase
) : ViewModel() {
    var uiState: FeedGridUiState by mutableStateOf(FeedGridUiState.Loading)

    fun onBottom(feedId: Int) {
        viewModelScope.launch {
            try {
                loadFeedUserCase.invoke(feedId)
            } catch (e: Exception) {
                Log.e("__TorangGridViewModel", e.toString())
            }
        }
    }

    fun onRefresh() {
        Log.d("__TorangGridViewModel", "onRefresh")
        viewModelScope.launch {
            uiState = (uiState as FeedGridUiState.Success).copy(isRefreshing = true)
            refreshFeedUseCase.invoke()
            uiState = (uiState as FeedGridUiState.Success).copy(isRefreshing = false)
        }
    }

    init {
        viewModelScope.launch {
            useCase.invoke().collect {
                uiState = FeedGridUiState.Success(it, false)
            }
        }
    }
}