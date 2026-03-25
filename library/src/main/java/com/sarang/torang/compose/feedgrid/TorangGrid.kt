package com.sarang.torang.compose.feedgrid

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * 그리드 형식으로 피드를 제공하는 화면
 */
@Composable
fun TorangGrid(
    modifier        : Modifier              = Modifier,
    listState       : LazyGridState         = rememberLazyGridState(),
    viewModel       : TorangGridViewModel   = hiltViewModel(),
    onFinishRefresh : () -> Unit            = {},
    onClickItem     : (Int) -> Unit         = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TorangGridContainer(
        modifier        = modifier,
        uiState         = uiState,
        listState       = listState,
        onFinishRefresh = onFinishRefresh,
        onRefresh       = viewModel::onRefresh,
        onBottom        = viewModel::onBottom,
        onClickItem     = onClickItem
    )
}

@Composable
fun TorangGridContainer(
    modifier        : Modifier          = Modifier,
    listState       : LazyGridState     = rememberLazyGridState(),
    uiState         : FeedGridUiState   = FeedGridUiState.Loading,
    onFinishRefresh : () -> Unit        = {},
    onRefresh       : () -> Unit        = {},
    onBottom        : (Int) -> Unit     = {},
    onClickItem     : (Int) -> Unit     = {}
) {
    LaunchedEffect(uiState) {
        if (uiState is FeedGridUiState.Success) {
            snapshotFlow { uiState.isRefreshing }.collect {
                if (!it) onFinishRefresh.invoke()
            }
        }
    }

    when (uiState) {
        is FeedGridUiState.Loading -> { TorangGridLoading() }
        is FeedGridUiState.Error -> { TorangGridError() }
        is FeedGridUiState.Success -> {
            TorangGridSuccess(
                uiState     = uiState,
                listState   = listState,
                onRefresh   = onRefresh,
                modifier    = modifier,
                onBottom    = onBottom,
                onClickItem = onClickItem
            )
        }
    }
}