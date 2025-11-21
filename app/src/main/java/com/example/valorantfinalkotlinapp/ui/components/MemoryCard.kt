package com.example.valorantfinalkotlinapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.ui.screens.MemoryGameCard

@Composable
fun MemoryCard(card: MemoryGameCard, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (card.isFlipped) 180f else 0f,
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable(onClick = onClick, enabled = !card.isFlipped),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (rotation < 90f) {
            // --- DOS DE LA CARTE ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            )
        } else {
            // --- FACE DE LA CARTE ---
            AsyncImage(
                model = card.agent.displayIcon,
                contentDescription = "Agent ${card.agent.displayName}",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .graphicsLayer { rotationY = 180f } // On contre-rote l'image
            )
        }
    }
}
