package com.sarang.torang.compose.feedgrid

sealed interface FeedGridUiState {
    data class  Error(val msg: String?) : FeedGridUiState
    object      Loading                 : FeedGridUiState
    data class  Success(
        val list            : List<FeedGridItemUiState>  = listOf(),
        val isRefreshing    : Boolean                   = false
    )                                   : FeedGridUiState
}