package com.example.valorantfinalkotlinapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorantfinalkotlinapp.data.local.AppDatabase
import com.example.valorantfinalkotlinapp.data.local.GameStat
import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.ui.components.MemoryCard
import com.example.valorantfinalkotlinapp.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

data class MemoryGameCard(val agent: Agent, val id: Int, val isFlipped: Boolean = false, val isFound: Boolean = false)

@Composable
fun GameScreen(mainViewModel: MainViewModel, innerPadding: PaddingValues) {
    val agents by mainViewModel.agents.collectAsStateWithLifecycle()
    var cards by remember { mutableStateOf<List<MemoryGameCard>>(emptyList()) }
    var flippedCards by remember { mutableStateOf<List<MemoryGameCard>>(emptyList()) }
    var isGameWon by remember { mutableStateOf(false) }
    var foundPairsCount by remember { mutableStateOf(0) }
    var moveCount by remember { mutableStateOf(0) }
    var startTime by remember { mutableStateOf(0L) }
    var gameAgents by remember { mutableStateOf<List<Agent>>(emptyList()) }
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = remember { db.gameStatDao() }

    fun restartGame() {
        if (agents.isNotEmpty()) {
            gameAgents = agents.shuffled().take(8)
            cards = (gameAgents + gameAgents)
                .mapIndexed { index, agent -> MemoryGameCard(agent, index) }
                .shuffled()
            flippedCards = emptyList()
            isGameWon = false
            foundPairsCount = 0
            moveCount = 0
            startTime = System.currentTimeMillis()
        }
    }

    LaunchedEffect(agents) {
        if (cards.isEmpty()) restartGame()
    }

    LaunchedEffect(flippedCards) {
        if (flippedCards.size == 2) {
            moveCount++
            delay(1000)
            val (firstCard, secondCard) = flippedCards
            if (firstCard.agent.uuid == secondCard.agent.uuid) {
                cards = cards.map { if (it.id == firstCard.id || it.id == secondCard.id) it.copy(isFound = true) else it }
                foundPairsCount++
            } else {
                cards = cards.map { if (it.id == firstCard.id || it.id == secondCard.id) it.copy(isFlipped = false) else it }
            }
            flippedCards = emptyList()
        }
    }

    LaunchedEffect(foundPairsCount) {
        if (foundPairsCount == 8) {
            delay(500)
            isGameWon = true
        }
    }

    // LaunchedEffect pour sauvegarder le score
    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            val duration = System.currentTimeMillis() - startTime
            val agentUuids = gameAgents.joinToString(",") { it.uuid }
            dao.insert(GameStat(moveCount = moveCount, durationInMillis = duration, agentUuids = agentUuids))
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Memory Game",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Coups: $moveCount",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cards, key = { it.id }) { card ->
                    MemoryCard(card = card) {
                        if (flippedCards.size < 2 && !card.isFlipped && !card.isFound) {
                            val newlyFlippedCard = card.copy(isFlipped = true)
                            cards = cards.map { if (it.id == card.id) newlyFlippedCard else it }
                            flippedCards = flippedCards + newlyFlippedCard
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { restartGame() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Recommencer")
            }
        }

        AnimatedVisibility(visible = isGameWon, enter = fadeIn(tween(500))) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
                    .clickable(enabled = true, onClick = { restartGame() }),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Victoire !",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "En $moveCount coups",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
                KonfettiView(
                    modifier = Modifier.fillMaxSize(),
                    parties = listOf(
                        Party(speed = 0f, maxSpeed = 30f, damping = 0.9f, spread = 360, colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def), emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100))
                    )
                )
            }
        }
    }
}
