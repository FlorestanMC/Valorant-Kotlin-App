package com.example.valorantfinalkotlinapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.models.Map

@Composable
fun MapCard(map: Map, modifier: Modifier = Modifier) {
    var isFlipped by remember { mutableStateOf(false) }

    // On renomme la variable d'animation pour éviter le conflit
    val flipRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "flipRotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                rotationY = flipRotation // On utilise la variable avec le nom unique
                cameraDistance = 8 * density
            }
            .clickable { isFlipped = !isFlipped },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // On affiche le recto ou le verso en fonction de l'angle de rotation
        if (flipRotation < 90f) {
            // --- RECTO DE LA CARTE ---
            Box(modifier = Modifier.height(200.dp)) {
                AsyncImage(
                    model = map.splash,
                    contentDescription = "Image de la map ${map.displayName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = map.displayName,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        } else {
            // --- VERSO DE LA CARTE ---
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .graphicsLayer { rotationY = 180f } // Correction pour l'inversion du texte
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = map.displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = map.narrativeDescription ?: "Aucune description disponible.")
                }
            }
        }
    }
}