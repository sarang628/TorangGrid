package com.sarang.torang.ui

interface LoadFeedUseCase {
    suspend fun invoke(lastFeedId: Int)
}