package com.learningwithmanos.uniexercise.heroes.source.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    val marvelApiService: MarvelApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApiService::class.java)
    }
}
