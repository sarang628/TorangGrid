package com.sarang.torang.compose.feedgrid.type

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias BottomDetectingLazyVerticalGridType = @Composable (
    BottomDetectingLazyVerticalGridData
) -> Unit

val LocalBottomDetectingLazyVerticalGridType = compositionLocalOf<BottomDetectingLazyVerticalGridType> {
    @Composable {
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            it.content.invoke(this)
        }
    }
}