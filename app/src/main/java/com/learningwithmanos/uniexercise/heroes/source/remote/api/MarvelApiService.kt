package com.learningwithmanos.uniexercise.heroes.source.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface MarvelApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String
    ): Response<MarvelCharactersResponse>
}


