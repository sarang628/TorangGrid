package com.sarang.torang.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun TorangGrid(
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit = { _, _, _, _, _ -> },
    urls: List<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(urls.size) {
            image.invoke(
                Modifier.size(128.dp),
                urls[it],
                30.dp,
                30.dp,
                ContentScale.Crop
            )
        }
    }
}