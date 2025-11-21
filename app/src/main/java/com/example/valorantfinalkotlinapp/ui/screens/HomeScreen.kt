package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorantfinalkotlinapp.ui.components.AgentCard
import com.example.valorantfinalkotlinapp.ui.components.MapCard
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel

@Composable
fun HomeScreen(mainViewModel: MainViewModel, innerPadding: PaddingValues, onMapClick: (String) -> Unit) {
    val agents by mainViewModel.agents.collectAsStateWithLifecycle()
    val maps by mainViewModel.maps.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(vertical = 16.dp)
    ) {
        // --- Section des Agents ---
        Text(
            text = "Agents",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(agents) { agent ->
                AgentCard(agent = agent)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Section des Maps ---
        Text(
            text = "Maps",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(maps) { valorantMap ->
                MapCard(map = valorantMap, onClick = { onMapClick(valorantMap.uuid) })
            }
        }
    }
}
