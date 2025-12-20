package com.sarang.torang.compose.feedgrid

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.compose.feedgrid.type.BottomDetectingLazyVerticalGridData
import com.sarang.torang.compose.feedgrid.type.LocalBottomDetectingLazyVerticalGridType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridImageLoaderType
import com.sarang.torang.compose.feedgrid.type.LocalTorangGridPullToRefresh
import com.sarang.torang.compose.feedgrid.type.TorangGridImageLoaderData
import com.sarang.torang.compose.feedgrid.type.TorangGridPullToRefreshData

@Composable
fun TorangGridSuccess(
    modifier    : Modifier                  = Modifier,
    showLog     : Boolean                   = false,
    tag         : String                    = "__TorangGridSuccess",
    uiState     : FeedGridUiState.Success   = FeedGridUiState.Success(),
    onRefresh   : () -> Unit                = {},
    onBottom    : (Int) -> Unit             = {},
    onClickItem : (Int) -> Unit             = {}
){
    LocalTorangGridPullToRefresh.current.invoke(
        TorangGridPullToRefreshData(
            onRefresh = onRefresh,
        ) {
            LocalBottomDetectingLazyVerticalGridType.current(
                BottomDetectingLazyVerticalGridData(
                    modifier                = modifier,
                    items                   = uiState.list.size,
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
                    content                 = { index ->
                        LocalTorangGridImageLoaderType.current.invoke(
                            TorangGridImageLoaderData(
                                modifier        = Modifier.size(128.dp)
                                                   .clickable(onClick = {
                                                       onClickItem.invoke(uiState.list[index].reviewId)
                                                   }),
                                url             = uiState.list[index].imageUrl,
                                iconSize        = 30.dp,
                                errorIconSize   = 30.dp,
                                contentScale    = ContentScale.Crop
                            )
                        )
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
        listOf(), false
    )
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}

private fun Boolean.d(
    tag: String,
    msg: String
) {
    if(this)Log.d(tag, msg)
}