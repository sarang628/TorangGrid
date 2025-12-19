package com.sarang.torang.usecase.feedgrid

interface LoadFeedGridUseCase {
    suspend fun invoke(lastFeedId: Int)
}