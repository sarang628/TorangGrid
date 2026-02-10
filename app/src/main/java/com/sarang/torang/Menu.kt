package com.sarang.torang

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

internal fun NavGraphBuilder.menu(navController : NavHostController){
    composable("menu"){
        Menu(navController)
    }
}

@Composable
internal fun Menu(navController : NavHostController){
    Column {
        Button({
            navController.navigate("torangGrid")
        }) { Text("torangGrid") }
        Button({
            navController.navigate("loginRepository")
        }) { Text("loginRepository") }
        Button({
            navController.navigate("feedRepository")
        }) { Text("feedRepository") }
    }
}