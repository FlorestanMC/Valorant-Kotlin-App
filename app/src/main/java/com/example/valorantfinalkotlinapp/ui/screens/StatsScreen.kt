package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel
import com.example.valorantfinalkotlinapp.viewmodels.StatsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun StatsScreen(mainViewModel: MainViewModel, statsViewModel: StatsViewModel = viewModel()) {
    val allStats by statsViewModel.allStats.collectAsState()
    val avgMoveCount by statsViewModel.averageMoveCount.collectAsState()
    val avgDuration by statsViewModel.averageDuration.collectAsState()
    val mostFrequentAgent by statsViewModel.mostFrequentAgent.collectAsState()
    val agents by mainViewModel.agents.collectAsState()

    val fullMostFrequentAgent = mostFrequentAgent?.let { freqAgent ->
        agents.find { it.uuid == freqAgent.uuid }
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Statistiques",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (allStats.isEmpty()) {
            item {
                Text(text = "Aucune partie jouée pour le moment.")
            }
        } else {
            item {
                // --- Cartes de statistiques agrégées ---
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(title = "Coups moyens", value = "%.1f".format(avgMoveCount), modifier = Modifier.weight(1f))
                    StatCard(title = "Temps moyen", value = formatDuration(avgDuration), modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                fullMostFrequentAgent?.let {
                    MostFrequentAgentCard(agent = it)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Historique des parties", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }

            // --- Historique des parties ---
            items(allStats) {
                stat ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Score: ${stat.moveCount} coups en ${formatDuration(stat.durationInMillis)}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(Date(stat.timestamp)),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = value, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MostFrequentAgentCard(agent: com.example.valorantfinalkotlinapp.models.Agent) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = agent.displayIcon, contentDescription = "Agent le plus fréquent", modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column {
                Text(text = "Agent porte-bonheur", style = MaterialTheme.typography.titleMedium)
                Text(text = agent.displayName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun formatDuration(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    return "%d:%02d".format(minutes, seconds)
}

