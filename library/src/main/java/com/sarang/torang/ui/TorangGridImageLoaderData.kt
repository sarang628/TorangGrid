package com.sarang.torang.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TorangGridImageLoaderData(
    val modifier        : Modifier = Modifier,
    val url             : String = "",
    val iconSize        : Dp = 30.dp,
    val errorIconSize   : Dp =  30.dp,
    val contentScale    : ContentScale = ContentScale.Fit
)