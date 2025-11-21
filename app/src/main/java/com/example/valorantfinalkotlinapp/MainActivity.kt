package com.example.valorantfinalkotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.valorantfinalkotlinapp.ui.screens.GameScreen
import com.example.valorantfinalkotlinapp.ui.screens.HomeScreen
import com.example.valorantfinalkotlinapp.ui.screens.MapDetailScreen
import com.example.valorantfinalkotlinapp.ui.screens.StatsScreen
import com.example.valorantfinalkotlinapp.ui.theme.ValorantFinalKotlinAppTheme
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantFinalKotlinAppTheme {
                val navController = rememberNavController()

                val items = listOf(
                    Screen.Home,
                    Screen.Game,
                    Screen.Stats
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(screen.route) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.Home.route) { // On enlève le padding ici
                        composable(Screen.Home.route) {
                            HomeScreen(
                                mainViewModel = mainViewModel,
                                innerPadding = innerPadding,
                                onMapClick = { mapUuid ->
                                    navController.navigate("map_detail/$mapUuid")
                                }
                            )
                        }
                        composable(Screen.Game.route) { GameScreen(mainViewModel, innerPadding) }
                        composable(Screen.Stats.route) { StatsScreen(mainViewModel) }
                        composable(
                            route = "map_detail/{mapUuid}",
                            arguments = listOf(navArgument("mapUuid") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val mapUuid = backStackEntry.arguments?.getString("mapUuid")
                            if (mapUuid != null) {
                                MapDetailScreen(mapUuid = mapUuid, mainViewModel = mainViewModel, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Sealed class pour représenter les écrans de l'application
sealed class Screen(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("Accueil", Icons.Default.Home)
    object Game : Screen("Jeu", Icons.Default.PlayArrow)
    object Stats : Screen("Stats", Icons.Default.Star)
}
