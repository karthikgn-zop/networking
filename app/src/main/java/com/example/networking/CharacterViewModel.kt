package com.example.networking

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networking.model.Character
import com.example.networking.network.RetrofitInstance
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _characters = mutableStateOf<List<Character>>(emptyList())
    val characters: State<List<Character>> = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCharacters()
                _characters.value = response.results
                Log.d("API_TEST", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", e.message ?: "Unknown error")
            }
        }
    }
}