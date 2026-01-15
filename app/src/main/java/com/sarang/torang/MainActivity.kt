package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.feed.FeedFlowRepository
import com.sarang.torang.repository.feed.FeedLoadRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sarang.torang.repository.test.feed.FeedRepositoryTest
import com.sarang.torang.ui.theme.TorangGridTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var loginRepository: LoginRepository

    @Inject lateinit var feedRepository: FeedRepository
    @Inject lateinit var feedLoadRepository: FeedLoadRepository
    @Inject lateinit var feedFlowRepository: FeedFlowRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangGridTheme {

                val list by feedLoadRepository.feeds.collectAsState(initial = emptyList())
                val snackBarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val listState = rememberLazyGridState()

                Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
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
                                Scaffold(floatingActionButton = {
                                    FloatingActionButton({
                                        scope.launch { listState.animateScrollToItem(0) }
                                    }) {
                                        Icon(imageVector = Icons.Default.KeyboardArrowUp,
                                             contentDescription = null) }
                                }) {
                                    Box(modifier = Modifier.padding(it)){
                                        ProvideTorangGrid(listState = listState)
                                    }
                                }
                            }
                            composable("loginRepository"){
                                LoginRepositoryTest(loginRepository)
                            }
                            composable("feedRepository"){
                                FeedRepositoryTest(feedRepository = feedRepository,
                                    feedLoadRepository = feedLoadRepository,
                                    feedFlowRepository = feedFlowRepository)
                            }
                        }

                    }
                }
            }
        }
    }
}