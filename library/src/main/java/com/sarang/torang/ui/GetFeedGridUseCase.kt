package com.sarang.torang.ui

import kotlinx.coroutines.flow.Flow

interface GetFeedGridUseCase {
    suspend fun invoke(): Flow<List<Pair<Int, String?>>>
}