package com.example.valorantfinalkotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.valorantfinalkotlinapp.navigation.Screen
import com.example.valorantfinalkotlinapp.ui.screens.BoxOpeningScreen
import com.example.valorantfinalkotlinapp.ui.screens.GameScreen
import com.example.valorantfinalkotlinapp.ui.screens.HomeScreen
import com.example.valorantfinalkotlinapp.ui.screens.InventoryScreen
import com.example.valorantfinalkotlinapp.ui.screens.MapDetailScreen
import com.example.valorantfinalkotlinapp.ui.screens.SplashScreen
import com.example.valorantfinalkotlinapp.ui.screens.StatsScreen
import com.example.valorantfinalkotlinapp.ui.theme.ValorantFinalKotlinAppTheme
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ValorantFinalKotlinAppTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))

                val items = listOf(
                    Screen.Home,
                    Screen.Game,
                    Screen.Stats,
                    Screen.BoxOpening,
                    Screen.Inventory
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        val currentRoute = currentDestination?.route

                        // Ne pas afficher la barre de navigation sur l'écran de démarrage
                        if (currentRoute != Screen.Splash.route) {
                            NavigationBar {
                                items.forEach { screen ->
                                    screen.icon?.let { // S'assurer que l'icône n'est pas nulle
                                        NavigationBarItem(
                                            icon = { Icon(it, contentDescription = null) },
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
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Screen.Splash.route) {
                        composable(Screen.Splash.route) {
                            SplashScreen(navController)
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(
                                mainViewModel = mainViewModel,
                                innerPadding = innerPadding,
                                onMapClick = { mapUuid ->
                                    navController.navigate(Screen.MapDetail.route.replace("{mapUuid}", mapUuid))
                                }
                            )
                        }
                        composable(Screen.Game.route) { GameScreen(mainViewModel, innerPadding) }
                        composable(Screen.Stats.route) { StatsScreen(mainViewModel) }
                        composable(Screen.BoxOpening.route) { BoxOpeningScreen(mainViewModel) }
                        composable(Screen.Inventory.route) { InventoryScreen(mainViewModel) }
                        composable(
                            route = Screen.MapDetail.route,
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
