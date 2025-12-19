package com.sarang.torang.compose.feedgrid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.feedgrid.FindAllFeedGridUseCase
import com.sarang.torang.usecase.feedgrid.LoadFeedGridUseCase
import com.sarang.torang.usecase.feedgrid.RefreshFeedGirdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorangGridViewModel @Inject constructor(
    private val getFeedGridUseCase  : FindAllFeedGridUseCase,
    private val loadFeedUserCase    : LoadFeedGridUseCase,
    private val refreshFeedUseCase  : RefreshFeedGirdUseCase
) : ViewModel() {
    val tag = "__TorangGridViewModel"
    var isRefreshing : MutableStateFlow<Boolean> = MutableStateFlow(false); private set
    val uiState: StateFlow<FeedGridUiState> = combine(getFeedGridUseCase.invoke(), isRefreshing){ feedGridUiState, isRefreshing ->
                                                                    FeedGridUiState.Success(list = feedGridUiState,
                                                                                            isRefreshing = isRefreshing)
                                                                }.stateIn(scope = viewModelScope,
                                                                         started = SharingStarted.WhileSubscribed(5000),
                                                                         initialValue = FeedGridUiState.Loading)


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
            isRefreshing.emit(true)
            refreshFeedUseCase.invoke()
            isRefreshing.emit(false)
        }
    }

}