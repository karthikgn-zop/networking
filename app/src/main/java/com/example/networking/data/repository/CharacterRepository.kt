package com.example.networking.data.repository

import com.example.networking.data.local.dao.CharacterDao
import com.example.networking.data.local.entity.SavedCharacter
import com.example.networking.data.remote.ApiService
import com.example.networking.model.Character

class CharacterRepository(
    private val api: ApiService,
    private val dao: CharacterDao
) {

    suspend fun fetchCharacters(page: Int): List<Character> {
        return api.getCharacters(page).results
    }

    suspend fun saveCharacter(character: Character) {
        dao.insert(
            SavedCharacter(
                id = character.id,
                name = character.name,
                image = character.image,
                species = character.species,
                status = character.status,
                gender = character.gender,
                origin = character.origin,
                location = character.location,
                episode = character.episode,
                created = character.created,
                url = character.url
            )
        )
    }

    suspend fun getSavedCharacters(): List<SavedCharacter> {
        return dao.getAllSaved()
    }

    suspend fun deleteCharacter(id: Int) {
        dao.deleteById(id)
    }

    suspend fun isSaved(id: Int): Boolean {
        return dao.isSaved(id)
    }
}