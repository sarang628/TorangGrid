package com.sarang.torang.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
        is FeedGridUiState.Loading -> {}
        is FeedGridUiState.Error -> {}
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
                            if (uiState.list.isNotEmpty())
                                viewModel.onBottom(uiState.list.last().first)
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
                    viewModel.onRefresh()
                }
            )
        }
    }
}