package com.sarang.torang.compose.feedgrid

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.feedgrid.GetFeedGridUseCase
import com.sarang.torang.usecase.feedgrid.LoadFeedUseCase
import com.sarang.torang.usecase.feedgrid.RefreshFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedGridUiState {
    data class  Error(val msg: String?) : FeedGridUiState
    object      Loading                 : FeedGridUiState
    data class  Success(
        val list            : List<Pair<Int, String?>>  = listOf(),
        val isRefreshing    : Boolean                   = false
    )                                   : FeedGridUiState
}

@HiltViewModel
class TorangGridViewModel @Inject constructor(
    private val getFeedGridUseCase  : GetFeedGridUseCase,
    private val loadFeedUserCase    : LoadFeedUseCase,
    private val refreshFeedUseCase  : RefreshFeedUseCase
) : ViewModel() {
    var uiState: FeedGridUiState by mutableStateOf(FeedGridUiState.Loading)
    val tag = "__TorangGridViewModel"

    fun onBottom(feedId: Int) {
        viewModelScope.launch {
            try {
                Log.d(tag, "onBottom feedId: $feedId")
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
            getFeedGridUseCase.invoke().collect {
                uiState = FeedGridUiState.Success(it, false)
            }
        }
    }
}