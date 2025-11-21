package com.example.valorantfinalkotlinapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.models.Agent

@Composable
fun AgentCard(agent: Agent, modifier: Modifier = Modifier) {
    var isFlipped by remember { mutableStateOf(false) }

    val flipRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "flipRotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                rotationY = flipRotation
                cameraDistance = 8 * density
            }
            .clickable { isFlipped = !isFlipped },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (flipRotation < 90f) {
            // --- RECTO DE LA CARTE ---
            Column(
                modifier = Modifier
                    .height(250.dp) // Hauteur fixe
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centrage vertical
            ) {
                AsyncImage(
                    model = agent.displayIcon,
                    contentDescription = "Icon de l'agent ${agent.displayName}",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = agent.displayName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                agent.role?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.displayName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            // --- VERSO DE LA CARTE ---
            Box(
                modifier = Modifier
                    .height(250.dp) // Hauteur fixe et identique
                    .graphicsLayer { rotationY = 180f } // Correction pour l'inversion
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState()) // Ajout du scroll
                ) {
                    Text(
                        text = agent.displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = agent.description,
                        style = MaterialTheme.typography.bodyMedium, // Police plus lisible
                        textAlign = TextAlign.Center // Texte centré
                    )
                }
            }
        }
    }
}
