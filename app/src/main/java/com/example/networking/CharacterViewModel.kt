package com.example.networking

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.networking.data.local.DatabaseProvider
import com.example.networking.data.remote.RetrofitInstance
import com.example.networking.data.repository.CharacterRepository
import com.example.networking.model.Character
import com.example.networking.model.Location
import com.example.networking.model.Origin
import kotlinx.coroutines.launch

class CharacterViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = CharacterRepository(
        api = RetrofitInstance.api,
        dao = DatabaseProvider
            .getDatabase(application)
            .characterDao()
    )

    private val _characters = mutableStateOf<List<Character>>(emptyList())
    val characters: State<List<Character>> = _characters

    private val _savedOnly = mutableStateOf(false)
    val savedOnly: State<Boolean> = _savedOnly

    private val _savedIds = mutableStateOf<Set<Int>>(emptySet())
    val savedIds: State<Set<Int>> = _savedIds

    private var currentPage = 1
    private var isLoading = false

    init {
        fetchCharacters()
        loadSavedIds()
    }

    private fun loadSavedIds() {
        viewModelScope.launch {
            val saved = repository.getSavedCharacters()
            _savedIds.value = saved.map { it.id }.toSet()
        }
    }

    fun fetchCharacters() {
        if (isLoading || _savedOnly.value) return

        viewModelScope.launch {
            try {
                isLoading = true
                val newCharacters = repository.fetchCharacters(currentPage)
                _characters.value += newCharacters
                currentPage++
            } catch (e: Exception) {
                Log.e("API_ERROR", e.message ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }

    fun toggleSave(character: Character) {
        viewModelScope.launch {
            val isSaved = repository.isSaved(character.id)
            if (isSaved) {
                repository.deleteCharacter(character.id)
            } else {
                repository.saveCharacter(character)
            }
            loadSavedIds()
        }
    }

    fun toggleSort() {
        _savedOnly.value = !_savedOnly.value

        viewModelScope.launch {
            if (_savedOnly.value) {
                val saved = repository.getSavedCharacters()
                _characters.value = saved.map {
                    Character(
                        id = it.id,
                        name = it.name,
                        image = it.image,
                        species = it.species,
                        status = it.status,
                        gender = "",
                        origin = Origin("", ""),
                        location = Location("", ""),
                        episode = emptyList(),
                        url = "",
                        created = ""
                    )
                }
            } else {
                _characters.value = emptyList()
                currentPage = 1
                fetchCharacters()
            }
        }
    }
}