package com.sarang.torang.compose.feedgrid.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias TorangGridImageLoaderType = @Composable (TorangGridImageLoaderData) -> Unit

val LocalTorangGridImageLoaderType = compositionLocalOf<TorangGridImageLoaderType>
{
    @Composable {

    }
}