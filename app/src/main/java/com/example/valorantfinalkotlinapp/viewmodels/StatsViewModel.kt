package com.example.valorantfinalkotlinapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantfinalkotlinapp.data.local.AppDatabase
import com.example.valorantfinalkotlinapp.data.local.GameStat
import com.example.valorantfinalkotlinapp.models.Agent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).gameStatDao()

    val allStats: StateFlow<List<GameStat>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val averageMoveCount: StateFlow<Double> = allStats.map { stats ->
        if (stats.isEmpty()) 0.0 else stats.map { it.moveCount }.average()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val averageDuration: StateFlow<Long> = allStats.map { stats ->
        if (stats.isEmpty()) 0L else stats.map { it.durationInMillis }.average().toLong()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val mostFrequentAgent: StateFlow<Agent?> = allStats.map { stats ->
        if (stats.isEmpty()) {
            null
        } else {
            val allAgentUuids = stats.flatMap { it.agentUuids.split(",") }
            val mostFrequentUuid = allAgentUuids.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
            // Note: Pour obtenir l'objet Agent complet, il faudra le croiser avec la liste des agents
            // qui sera disponible dans la vue.
            mostFrequentUuid?.let { uuid ->
                // Ceci est un placeholder. La logique de recherche de l'agent sera dans la vue.
                // On ne peut pas facilement accéder au MainViewModel ici.
                // On retourne un Agent partiel pour l'instant.
                Agent(uuid = uuid, displayName = "", displayIcon = "", description = "", role = null, abilities = emptyList())
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

}
