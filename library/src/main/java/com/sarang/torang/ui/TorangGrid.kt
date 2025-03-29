package com.sarang.torang.ui

import android.util.Log
import android.widget.ProgressBar
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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
    viewModel: TorangGridViewModel = hiltViewModel(),
    modifier: Modifier,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    bottomDetectingLazyVerticalGrid: @Composable (
        modifier: Modifier,
        items: Int,
        columns: GridCells,
        contentPadding: PaddingValues,
        verticalArrangement: Arrangement.Vertical,
        horizontalArrangement: Arrangement.Horizontal,
        onBottom: () -> Unit,
        content: @Composable (index: Int) -> Unit
    ) -> Unit = { _, _, _, _, _, _, _, _ -> },
    pullToRefreshLayout: @Composable (@Composable () -> Unit, onRefresh: () -> Unit) -> Unit,
    onFinishRefresh: () -> Unit = {}
) {
    val uiState = viewModel.uiState
    _TorangGrid(
        uiState,
        modifier,
        image,
        bottomDetectingLazyVerticalGrid,
        pullToRefreshLayout,
        onFinishRefresh,
        viewModel::onRefresh,
        viewModel::onBottom
    )

}

@Composable
fun _TorangGrid(
    uiState: FeedGridUiState,
    modifier: Modifier,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    bottomDetectingLazyVerticalGrid: @Composable (
        modifier: Modifier,
        items: Int,
        columns: GridCells,
        contentPadding: PaddingValues,
        verticalArrangement: Arrangement.Vertical,
        horizontalArrangement: Arrangement.Horizontal,
        onBottom: () -> Unit,
        content: @Composable (index: Int) -> Unit
    ) -> Unit = { _, _, _, _, _, _, _, _ -> },
    pullToRefreshLayout: @Composable (@Composable () -> Unit, onRefresh: () -> Unit) -> Unit,
    onFinishRefresh: () -> Unit = {},
    onRefresh: () -> Unit,
    onBottom: (Int) -> Unit
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
            pullToRefreshLayout.invoke(
                {
                    bottomDetectingLazyVerticalGrid.invoke(
                        modifier,
                        uiState.list.size,
                        GridCells.Fixed(3),
                        PaddingValues(1.dp),
                        Arrangement.spacedBy(1.dp),
                        Arrangement.spacedBy(1.dp),
                        {
                            if (uiState.list.isNotEmpty()) {
                                onBottom.invoke(uiState.list.last().first)
                            }
                        },
                        { index ->
                            image.invoke(
                                Modifier.size(128.dp),
                                uiState.list[index].second ?: "",
                                30.dp,
                                30.dp,
                                ContentScale.Crop
                            )
                        }
                    )
                }, {
                    onRefresh.invoke()
                }
            )
        }
    }
}

@Preview
@Composable
fun ErrorTest() {
    val uiState = FeedGridUiState.Error("test")
    _TorangGrid(
        uiState = uiState,
        modifier = Modifier,
        image = { _, _, _, _, _ -> },
        bottomDetectingLazyVerticalGrid = { _, _, _, _, _, _, _, _ -> },
        pullToRefreshLayout = { _, _ -> },
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}

@Preview
@Composable
fun ProgressTest() {
    val uiState = FeedGridUiState.Loading
    _TorangGrid(
        uiState = uiState,
        modifier = Modifier,
        image = { _, _, _, _, _ -> },
        bottomDetectingLazyVerticalGrid = { _, _, _, _, _, _, _, _ -> },
        pullToRefreshLayout = { _, _ -> },
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}

@Preview
@Composable
fun SuccessTest() {
    val uiState = FeedGridUiState.Success(
        listOf(Pair(0, "")), false
    )
    _TorangGrid(
        uiState = uiState,
        modifier = Modifier,
        image = { _, _, _, _, _ -> },
        bottomDetectingLazyVerticalGrid = { _, _, _, _, _, _, _, _ -> },
        pullToRefreshLayout = { _, _ -> },
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}