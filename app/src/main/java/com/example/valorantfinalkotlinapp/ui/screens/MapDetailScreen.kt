package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel
import kotlin.math.roundToInt

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
        val currentMap = map
        if (currentMap == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AsyncImage(
                    model = currentMap.displayIcon,
                    contentDescription = "Image de la carte ${currentMap.displayName}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                val xMultiplier = currentMap.xMultiplier ?: 1f
                val yMultiplier = currentMap.yMultiplier ?: 1f
                val xScalar = currentMap.xScalarToAdd ?: 0f
                val yScalar = currentMap.yScalarToAdd ?: 0f

                val containerWidth = constraints.maxWidth.toFloat()
                val containerHeight = constraints.maxHeight.toFloat()

                val imageAspectRatio = 1f
                val containerAspectRatio = containerWidth / containerHeight

                val renderedImageWidth: Float
                val renderedImageHeight: Float

                if (imageAspectRatio > containerAspectRatio) {
                    renderedImageWidth = containerWidth
                    renderedImageHeight = containerWidth / imageAspectRatio
                } else {
                    renderedImageHeight = containerHeight
                    renderedImageWidth = containerHeight * imageAspectRatio
                }

                val offsetX = (containerWidth - renderedImageWidth) / 2
                val offsetY = (containerHeight - renderedImageHeight) / 2

                currentMap.callouts?.forEach { callout ->
                    val normalizedX = (callout.location.y * xMultiplier) + xScalar
                    val normalizedY = (callout.location.x * yMultiplier) + yScalar

                    val imageX = normalizedX * renderedImageWidth
                    val imageY = normalizedY * renderedImageHeight

                    var finalX = imageX + offsetX
                    var finalY = imageY + offsetY

                    // --- AJUSTEMENT GLOBAL ---
                    val globalOffsetX = -40f // Négatif pour aller à gauche
                    val globalOffsetY = -15f // Négatif pour monter
                    finalX += globalOffsetX
                    finalY += globalOffsetY

                    // --- AJUSTEMENTS SPÉCIFIQUES ---
                    when (callout.regionName) {
                        // Ajoutez des cas ici si nécessaire
                    }

                    Text(
                        text = callout.regionName,
                        modifier = Modifier
                            .offset { IntOffset(finalX.roundToInt(), finalY.roundToInt()) }
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 1.dp, vertical = 0.dp),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
        }
    }
}
