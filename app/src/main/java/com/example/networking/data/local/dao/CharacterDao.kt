package com.example.networking.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.networking.data.local.entity.SavedCharacter

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: SavedCharacter)

    @Query("SELECT * FROM saved_characters")
    suspend fun getAllSaved(): List<SavedCharacter>

    @Query("DELETE FROM saved_characters WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_characters WHERE id = :id)")
    suspend fun isSaved(id: Int): Boolean
}