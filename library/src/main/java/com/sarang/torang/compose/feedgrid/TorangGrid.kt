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
 * @param viewModel 토랑 그리드 뷰모델
 * @param modifier Modifier
 * @param onBottom 그리드 하단 터치 콜백
 * @param image 이미지 로드 모듈을 외부에서 받아 사용
 * @param bottomDetectingLazyVerticalGrid 하단 감지 그리드를 외부에서 받아 사용
 * @param pullToRefreshLayout 당겨서 새로고침 레이아웃
 * @param onFinishRefresh 새로고침 데이터 갱신 완료 콜백
 */
@Composable
fun TorangGrid(
    modifier        : Modifier              = Modifier,
    viewModel       : TorangGridViewModel   = hiltViewModel(),
    showLog         : Boolean               = false,
    onFinishRefresh : () -> Unit            = {},
    onClickItem     : (Int) -> Unit         = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TorangGridContainer(
        uiState         = uiState,
        showLog         = showLog,
        modifier        = modifier,
        onFinishRefresh = onFinishRefresh,
        onRefresh       = viewModel::onRefresh,
        onBottom        = viewModel::onBottom,
        onClickItem     = onClickItem
    )
}

@Composable
fun TorangGridContainer(
    modifier        : Modifier      = Modifier,
    uiState         : FeedGridUiState,
    showLog         : Boolean       = false,
    onFinishRefresh : () -> Unit    = {},
    onRefresh       : () -> Unit    = {},
    onBottom        : (Int) -> Unit = {},
    onClickItem     : (Int) -> Unit = {}
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
                showLog     = showLog,
                onRefresh   = onRefresh,
                modifier    = modifier,
                onBottom    = onBottom,
                onClickItem = onClickItem
            )
        }
    }
}

private fun Boolean.d(
    tag: String,
    msg: String
) {
    if(this)Log.d(tag, msg)
}

@Preview
@Composable
fun ErrorTest() {
    val uiState = FeedGridUiState.Error("test")
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}

@Preview
@Composable
fun ProgressTest() {
    val uiState = FeedGridUiState.Loading
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}