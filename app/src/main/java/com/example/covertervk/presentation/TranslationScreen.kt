package com.example.covertervk.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.covertervk.domain.model.Translation

@Composable
fun TranslationScreen(
    viewModel: TranslationViewModel,
    onOpenFavorites: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Введите слово") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { viewModel.translate(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перевести")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onOpenFavorites,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Избранное")
        }

        Spacer(Modifier.height(16.dp))
        Text("История", style = MaterialTheme.typography.titleMedium)

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.history, key = { it.id ?: 0 }) { item ->
                TranslationItem(
                    translation = item,
                    onToggleFavorite = { viewModel.toggleFavorite(item.id ?: 0) },
                    onDelete = { viewModel.deleteFromHistory(item.id ?: 0) }
                )
            }
        }
    }
}

@Composable
fun TranslationItem(
    translation: Translation,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = translation.text, style = MaterialTheme.typography.bodyLarge)
                Text(text = translation.translation, style = MaterialTheme.typography.bodyMedium)
                translation.transcription?.let {
                    Text(text = "[$it]", style = MaterialTheme.typography.bodySmall)
                }
            }
            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        if (translation.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
