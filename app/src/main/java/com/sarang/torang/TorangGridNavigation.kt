package com.sarang.torang

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun TorangGridNavigation(navController : NavHostController,
                                 loginRepository : @Composable ()->Unit = {},
                                 feedRepository : @Composable ()->Unit = {},
                                 torangGrid : @Composable ()->Unit = {},
){
    NavHost(navController = navController,
        startDestination = "menu") {
        menu(navController)
        composable("torangGrid"){ torangGrid() }
        composable("loginRepository"){ loginRepository() }
        composable("feedRepository"){ feedRepository() }
    }
}