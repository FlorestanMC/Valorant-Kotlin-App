package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapDetailScreen(mapUuid: String, mainViewModel: MainViewModel, navController: NavController) {
    val map by mainViewModel.getMapByUuid(mapUuid).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(map?.displayName ?: "Chargement...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (map == null) {
                CircularProgressIndicator()
            } else {
                // Affiche l'image de la carte en utilisant Coil
                AsyncImage(
                    model = map?.displayIcon,
                    contentDescription = "Image de la carte ${map?.displayName}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit // ou ContentScale.Crop selon le rendu souhaité
                )
            }
        }
    }
}
