package com.example.networking.model

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val episode: List<String>,
    val url: String,
    val created: String
)