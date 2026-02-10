package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.compose.feedgrid.FeedGridItemUiState
import com.sarang.torang.usecase.feedgrid.FindAllFeedGridUseCase
import com.sarang.torang.usecase.feedgrid.LoadFeedGridUseCase
import com.sarang.torang.usecase.feedgrid.RefreshFeedGirdUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FindAllFeedGridUseCaseTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var findAllFeedGridUseCase: FindAllFeedGridUseCase
    @Inject lateinit var loadFeedGridUseCase : LoadFeedGridUseCase

    @Inject lateinit var refreshFeedUseCase  : RefreshFeedGirdUseCase

    @Before fun setUp() = runTest { hiltRule.inject()}

    @Test
    fun test() = runTest {
        loadFeedGridUseCase.invoke(Int.MAX_VALUE)
        val result : List<FeedGridItemUiState> = findAllFeedGridUseCase.invoke().first()
        Log.d("__FindAllFeedGridUseCaseTest", GsonBuilder().setPrettyPrinting().create().toJson(result))
    }

}