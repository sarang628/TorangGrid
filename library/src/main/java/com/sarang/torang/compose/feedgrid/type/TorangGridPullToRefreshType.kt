package com.sarang.torang.compose.feedgrid.type

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

typealias TorangGridPullToRefreshType = @Composable (TorangGridPullToRefreshData) -> Unit

data class TorangGridPullToRefreshData(
    val onRefresh: () -> Unit,
    val content : @Composable () -> Unit
)

val LocalTorangGridPullToRefresh = compositionLocalOf<TorangGridPullToRefreshType> {
    @Composable {
        Log.w("__LocalPullToRefreshLayoutType", "pullToRefreshLayout is not set");
        Box(modifier = Modifier.fillMaxSize()) {
            it.content()
        }
    }
}