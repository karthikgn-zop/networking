package com.example.networking.network

import com.example.networking.model.CharacterResponse
import retrofit2.http.GET

interface ApiService{
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}