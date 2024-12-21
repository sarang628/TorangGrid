package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.FeedRepositoryTest
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.ui.TorangGrid
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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.fillMaxSize())
                    {
                        Column(
                            Modifier
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            TorangGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(LocalConfiguration.current.screenHeightDp.dp),
                                image = provideTorangAsyncImage()
                            )
                            LoginRepositoryTest(loginRepository)
                            FeedRepositoryTest(feedRepository)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    TorangGrid(
        modifier = Modifier,
        image = provideTorangAsyncImage(),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TorangGridTheme {
        Greeting("Android")
    }
}