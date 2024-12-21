package com.sarang.torang.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> }
) {
    val uiState = viewModel.uiState
    when (uiState) {
        is FeedGridUiState.Loading -> {}
        is FeedGridUiState.Error -> {}
        is FeedGridUiState.Success -> {
            LazyVerticalGrid(
                modifier = modifier,
                contentPadding = PaddingValues(1.dp),
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(uiState.list.size) {
                    image.invoke(
                        Modifier.size(128.dp),
                        uiState.list[it] ?: "",
                        30.dp,
                        30.dp,
                        ContentScale.Crop
                    )
                }
            }
        }
    }
}