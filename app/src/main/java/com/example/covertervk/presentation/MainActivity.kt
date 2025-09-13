package com.example.covertervk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.covertervk.presentation.theme.ui.CoverterVkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TranslationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoverterVkTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "translation"
                ) {
                    composable("translation") {
                        TranslationScreen(
                            viewModel = viewModel,
                            onOpenFavorites = { navController.navigate("favorites") }
                        )
                    }
                    composable("favorites") {
                        FavoritesScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
