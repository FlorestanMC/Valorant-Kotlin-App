package com.example.valorantfinalkotlinapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Home : Screen("Accueil", Icons.Default.Home)
    object Game : Screen("Jeu", Icons.Default.PlayArrow)
    object Stats : Screen("Stats", Icons.Default.Star)
    object BoxOpening : Screen("Boîtes", Icons.Default.PlayArrow)
    object Inventory : Screen("Inventaire", Icons.Default.Star)
    object MapDetail : Screen("map_detail/{mapUuid}")
}
