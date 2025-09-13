package com.example.covertervk.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    viewModel: TranslationViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Spacer(Modifier.height(40.dp))
        Text("Избранное", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.favorites, key = { it.id ?: 0 }) { item ->
                TranslationItem(
                    translation = item,
                    onToggleFavorite = { viewModel.toggleFavorite(item.id ?: 0) },
                    onDelete = { viewModel.deleteFromHistory(item.id ?: 0) }
                )
            }
        }
        Spacer(Modifier.height(40.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth(),) {
            Text("Назад")
        }
    }
}
