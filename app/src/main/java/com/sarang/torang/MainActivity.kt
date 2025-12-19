package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.di.feedgrid_di.ProvideTorangGrid
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.FeedRepositoryTest
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sarang.torang.ui.theme.TorangGridTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var feedRepository: FeedRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangGridTheme {

                val list by feedRepository.feeds.collectAsState(initial = emptyList())
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }) { innerPadding ->
                    Box(Modifier.fillMaxSize().padding(innerPadding))
                    {
                        NavHost(modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = "menu"
                        ) {
                            composable("menu"){
                                Column() {
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
                            composable("torangGrid"){
                                ProvideTorangGrid()
                            }
                            composable("loginRepository"){
                                LoginRepositoryTest(loginRepository)
                            }
                            composable("feedRepository"){
                                FeedRepositoryTest(feedRepository)
                            }
                        }

                    }
                }
            }
        }
    }
}