package com.sarang.torang.compose.feedgrid

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ErrorTest() {
    val uiState = FeedGridUiState.Error("test")
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}

@Preview
@Composable
fun ProgressTest() {
    val uiState = FeedGridUiState.Loading
    TorangGridContainer(
        uiState = uiState,
        modifier = Modifier,
        onFinishRefresh = {},
        onBottom = { _ -> },
        onRefresh = {}
    )
}