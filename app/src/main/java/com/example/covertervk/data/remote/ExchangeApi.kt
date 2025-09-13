package com.example.covertervk.data.remote

import com.example.covertervk.data.remote.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {
    @GET("/api/public/v1/words/search")
    suspend fun searchWord(
        @Query("search") query: String,
        @Query("pageSize") pageSize: Int = 1
    ): List<WordDto>
}
