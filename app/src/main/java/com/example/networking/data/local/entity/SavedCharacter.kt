package com.example.networking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.networking.model.Location
import com.example.networking.model.Origin

@Entity(tableName = "saved_characters")
data class SavedCharacter(
    @PrimaryKey val id: Int,
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