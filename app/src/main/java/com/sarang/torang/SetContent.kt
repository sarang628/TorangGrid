package com.sarang.torang

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sryang.torang.ui.TorangTheme

fun ComponentActivity.setContent(
    content: @Composable () -> Unit
){
    setContent {
        TorangTheme {
            val snackBarHostState = remember { SnackbarHostState() }

            Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }) { innerPadding ->
                Box(Modifier.fillMaxSize().padding(innerPadding))
                {
                    content()
                }
            }
        }
    }
}