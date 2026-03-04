package com.example.networking.data.local

import androidx.room.TypeConverter
import com.example.networking.model.Location
import com.example.networking.model.Origin
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrigin(origin: Origin): String = gson.toJson(origin)

    @TypeConverter
    fun toOrigin(json: String): Origin = gson.fromJson(json, Origin::class.java)

    @TypeConverter
    fun fromLocation(location: Location): String = gson.toJson(location)

    @TypeConverter
    fun toLocation(json: String): Location = gson.fromJson(json, Location::class.java)

    @TypeConverter
    fun fromEpisodeList(episodes: List<String>): String = gson.toJson(episodes)

    @TypeConverter
    fun toEpisodeList(json: String): List<String> =
        gson.fromJson(json, Array<String>::class.java).toList()
}