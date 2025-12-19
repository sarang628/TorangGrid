package com.sarang.torang.usecase.feedgrid

interface LoadFeedUseCase {
    suspend fun invoke(lastFeedId: Int)
}