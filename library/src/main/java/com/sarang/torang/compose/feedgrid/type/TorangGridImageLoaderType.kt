package com.sarang.torang.compose.feedgrid.type

import androidx.appcompat.R
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.res.painterResource

typealias TorangGridImageLoaderType = @Composable (TorangGridImageLoaderData) -> Unit

val LocalTorangGridImageLoaderType = compositionLocalOf<TorangGridImageLoaderType>
{
    @Composable {
        Icon(modifier = it.modifier,
             imageVector = Icons.Default.AccountCircle,
             contentDescription = null)
    }
}