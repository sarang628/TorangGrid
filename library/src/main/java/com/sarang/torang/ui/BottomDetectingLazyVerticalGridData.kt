package com.sarang.torang.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class BottomDetectingLazyVerticalGridData(
    val modifier                : Modifier,
    val items                   : Int,
    val columns                 : GridCells,
    val contentPadding          : PaddingValues,
    val verticalArrangement     : Arrangement.Vertical,
    val horizontalArrangement   : Arrangement.Horizontal,
    val onBottom                : () -> Unit,
    val content                 : @Composable (index: Int) -> Unit
)