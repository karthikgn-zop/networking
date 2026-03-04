package com.example.networking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.networking.model.Character


@Preview(showBackground = true)
@Composable
private fun CharacterCardPreview() {
    MaterialTheme {
        CharacterCard(
            character = com.example.networking.model.Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                gender = "Male",
                origin = com.example.networking.model.Origin(name = "Earth", url = ""),
                location = com.example.networking.model.Location(name = "Earth", url = ""),
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                url = "",
                created = ""
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(viewModel: CharacterViewModel = viewModel()) {
    val characters by viewModel.characters
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Rick and Morty Characters")
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Characters Count: ${characters.size}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(characters) { character ->
                CharacterCard(character)
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Species: ${character.species}"
            )
            Text(
                text = "Status: ${character.status}"
            )
            Text(
                text = "Gender: ${character.gender}"
            )
            Text(text = "Origin: ${character.origin.name}")
            Text(text = "Episodes: ${character.episode.size}")
            Text(text = "Location: ${character.location.name}")
        }
    }
}