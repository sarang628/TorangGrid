package com.sarang.torang.compose.feedgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feedgrid.type.BottomDetectingLazyVerticalGridData
import com.sarang.torang.compose.feedgrid.type.LocalBottomDetectingLazyVerticalGridType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridImageLoaderType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridPullToRefresh
import com.sarang.torang.compose.feedgrid.type.TorangGridImageLoaderData
import com.sarang.torang.compose.feedgrid.type.TorangGridPullToRefreshData

val TAG : String = "__TorangGridSuccess"
@Composable
fun TorangGridSuccess(
    modifier    : Modifier                  = Modifier,
    listState   : LazyGridState             = rememberLazyGridState(),
    uiState     : FeedGridUiState.Success   = FeedGridUiState.Success(),
    onRefresh   : () -> Unit                = {},
    onBottom    : (Int) -> Unit             = {},
    onClickItem : (Int) -> Unit             = {},
    onSearch    : () -> Unit                = {}
){
    LocalTorangGridPullToRefresh.current.invoke(
        TorangGridPullToRefreshData(
            onRefresh = onRefresh,
        ) {
            LocalBottomDetectingLazyVerticalGridType.current(
                BottomDetectingLazyVerticalGridData(
                    modifier                = modifier,
                    listState               = listState,
                    columns                 = GridCells.Fixed(3),
                    contentPadding          = PaddingValues(1.dp),
                    verticalArrangement     = Arrangement.spacedBy(1.dp),
                    horizontalArrangement   = Arrangement.spacedBy(1.dp),
                    onBottom                = {
                        if (uiState.list.isNotEmpty()) {
                            onBottom.invoke(uiState.list.last().reviewId)
                        }else{
                            onBottom.invoke(Int.MAX_VALUE)
                        }
                    },
                    content                 = {
                        item(span = {
                            // 현재 그리드의 최대 가로 스팬(컬럼 수)만큼 차지하도록 설정
                            GridItemSpan(maxLineSpan)
                        }) {
                            SearchView(onSearch = onSearch)
                        }
                        items(uiState.list){
                            LocalTorangGridImageLoaderType.current.invoke(
                                TorangGridImageLoaderData(
                                    modifier        = Modifier.size(128.dp)
                                                              .clickable(onClick = { onClickItem.invoke(it.reviewId) }),
                                    url             = it.imageUrl,
                                    iconSize        = 30.dp,
                                    errorIconSize   = 30.dp,
                                    contentScale    = ContentScale.Crop
                                )
                            )
                        }
                    }
                )
            )
        }
    )
}

@Preview
@Composable
fun SuccessTest() {
    val uiState = FeedGridUiState.Success(
        listOf(
            FeedGridItemUiState(0, ""),
            FeedGridItemUiState(0, ""),
            FeedGridItemUiState(0, ""),
            FeedGridItemUiState(0, ""),
            FeedGridItemUiState(0, ""),
        ), false
    )
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}