package com.sarang.torang.compose.feedgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val isSearch by viewModel.isSearch.collectAsStateWithLifecycle()
    TorangGridContainer(modifier        = modifier,
                        uiState         = uiState,
                        isSearch        = isSearch,
                        listState       = listState,
                        onFinishRefresh = onFinishRefresh,
                        onRefresh       = viewModel::onRefresh,
                        onBottom        = viewModel::onBottom,
                        onClickItem     = onClickItem,
                        onSearch        = { viewModel.onSearch() },
                        onSearchBack    = { viewModel.onSearchBack() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorangGridContainer(
    modifier        : Modifier          = Modifier,
    listState       : LazyGridState     = rememberLazyGridState(),
    uiState         : FeedGridUiState   = FeedGridUiState.Loading,
    isSearch        : Boolean           = false,
    onFinishRefresh : () -> Unit        = {},
    onRefresh       : () -> Unit        = {},
    onBottom        : (Int) -> Unit     = {},
    onClickItem     : (Int) -> Unit     = {},
    onSearch        : () -> Unit        = {},
    onSearchBack    : () -> Unit        = {}
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
            TorangGridSuccess(uiState     = uiState,
                              listState   = listState,
                              onRefresh   = onRefresh,
                              modifier    = modifier,
                              onBottom    = onBottom,
                              onClickItem = onClickItem,
                              onSearch    = onSearch)
        }
    }

    if(isSearch){
        TextSearch(onSearchBack = onSearchBack)
    }
}