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
    private  var currentPage=1
    private  var isLoading=false
    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        if(isLoading) return

        viewModelScope.launch {
            try {
                isLoading=true
                val response = RetrofitInstance.api.getCharacters(currentPage)
                _characters.value += response.results
                currentPage++
                isLoading=false
                Log.d("API_TEST", response.toString())
            } catch (e: Exception) {
                isLoading=false
                e.printStackTrace()
                Log.e("API_ERROR", e.message ?: "Unknown error")
            }
        }
    }
}