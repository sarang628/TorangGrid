package com.sarang.torang.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun TorangGrid(
    viewModel: TorangGridViewModel = hiltViewModel(),
    modifier: Modifier,
    onBottom: () -> Unit = {},
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
    ) -> Unit = { _, _, _, _, _, _, _, _ -> }
) {
    when (val uiState = viewModel.uiState) {
        is FeedGridUiState.Loading -> {}
        is FeedGridUiState.Error -> {}
        is FeedGridUiState.Success -> {
            bottomDetectingLazyVerticalGrid.invoke(
                modifier,
                uiState.list.size,
                GridCells.Fixed(3),
                PaddingValues(1.dp),
                Arrangement.spacedBy(1.dp),
                Arrangement.spacedBy(1.dp),
                onBottom,
                { index ->
                    image.invoke(
                        Modifier.size(128.dp),
                        uiState.list[index] ?: "",
                        30.dp,
                        30.dp,
                        ContentScale.Crop
                    )
                }
            )
        }
    }
}