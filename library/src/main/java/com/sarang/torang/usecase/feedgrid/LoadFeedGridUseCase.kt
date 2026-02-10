package com.sarang.torang.usecase.feedgrid

interface LoadFeedGridUseCase {
    /**
     * @param lastFeedId 가장 최신 리스트 요청 시 Int.MAX_VALUE 로 보내기
     */
    suspend fun invoke(lastFeedId: Int)
}