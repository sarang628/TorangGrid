package com.sarang.torang.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias BottomDetectingLazyVerticalGridType = @Composable (
    BottomDetectingLazyVerticalGridData
) -> Unit

val LocalBottomDetectingLazyVerticalGridType = compositionLocalOf<BottomDetectingLazyVerticalGridType> {
    @Composable {
        it.content(0)
    }
}