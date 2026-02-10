package com.sarang.torang.compose.feedgrid

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.compose.feedgrid.type.BottomDetectingLazyVerticalGridData
import com.sarang.torang.compose.feedgrid.type.LocalBottomDetectingLazyVerticalGridType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridImageLoaderType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridPullToRefresh
import com.sarang.torang.compose.feedgrid.type.TorangGridImageLoaderData
import com.sarang.torang.compose.feedgrid.type.TorangGridPullToRefreshData

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
            snapshotFlow {
                uiState.isRefreshing
            }.collect {
                if (!it)
                    onFinishRefresh.invoke()
            }
        }
    }

    when (uiState) {
        is FeedGridUiState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
        }

        is FeedGridUiState.Error -> {
            Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Refresh, "")
                    }
                    Text("error")
                }
            }
        }

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