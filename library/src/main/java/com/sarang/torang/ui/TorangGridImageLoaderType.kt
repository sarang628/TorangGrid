package com.sarang.torang.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias TorangGridImageLoaderType = @Composable (TorangGridImageLoaderData) -> Unit

val LocalTorangGridImageLoaderType = compositionLocalOf<TorangGridImageLoaderType>
{
    @Composable {

    }
}