package com.sarang.torang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sarang.torang.di.feedgrid_di.ProvideTorangGrid
import kotlinx.coroutines.launch

internal fun NavGraphBuilder.torangGridTest(){
    composable("torangGrid"){
        val scope = rememberCoroutineScope()
        val listState = rememberLazyGridState()
        val floatingButton = @Composable {
                FloatingActionButton({
                    scope.launch { listState.animateScrollToItem(0) }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null) }
        }
        Scaffold(floatingActionButton = floatingButton) {
            Box(modifier = Modifier.padding(it)){
                ProvideTorangGrid(listState = listState)
            }
        }
    }
}

@Composable
internal fun TorangGridTest(){

}