package com.sarang.torang.usecase.feedgrid

import com.sarang.torang.compose.feedgrid.FeedGridItemUiState
import kotlinx.coroutines.flow.Flow

interface FindAllFeedGridUseCase {
    fun invoke(): Flow<List<FeedGridItemUiState>>
}