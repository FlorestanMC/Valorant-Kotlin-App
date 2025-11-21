package com.example.valorantfinalkotlinapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stats")
data class GameStat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val moveCount: Int,
    val durationInMillis: Long, // Temps de jeu en millisecondes
    val agentUuids: String, // Liste des UUIDs des agents, séparés par des virgules
    val timestamp: Long = System.currentTimeMillis()
)
