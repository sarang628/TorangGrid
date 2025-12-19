package com.sarang.torang.usecase.feedgrid

import kotlinx.coroutines.flow.Flow

interface GetFeedGridUseCase {
    suspend fun invoke(): Flow<List<Pair<Int, String?>>>
}