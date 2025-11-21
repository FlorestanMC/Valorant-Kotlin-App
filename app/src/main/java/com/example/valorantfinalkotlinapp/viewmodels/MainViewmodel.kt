package com.example.valorantfinalkotlinapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.models.Map
import com.example.valorantfinalkotlinapp.repositories.ValRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val agents = MutableStateFlow<List<Agent>>(emptyList()) // état pour la liste des agents
    val maps = MutableStateFlow<List<Map>>(emptyList()) // état pour la liste des maps


    init {
        getAgents() // On récupère les agents dès la création du ViewModel
        getMaps() // On récupère les maps dès la création du ViewModel
    }

    fun getAgents() {
        viewModelScope.launch {
            try {
                // On appelle la nouvelle fonction au pluriel
                val fetchedAgents = ValRepository.getAgents()
                agents.value = fetchedAgents
                // On affiche le résultat dans Logcat
                Log.d("MainViewModel", "Correctement récupérés :  ${fetchedAgents.size} agents.")
                Log.d("MainViewModel", "Premier Agent récupéré : ${fetchedAgents.firstOrNull()}")
            } catch (e: Exception) {
                // S'il y a une erreur, on l'affiche aussi
                Log.e("MainViewModel", "Une erreur s'est produite lors du fetch des agents: ", e)
            }
        }
    }

    fun getMaps() {
        viewModelScope.launch {
            try {
                // On appelle la nouvelle fonction au pluriel
                val fetchedMaps = ValRepository.getMaps()
                maps.value = fetchedMaps
                // On affiche le résultat dans Logcat
                Log.d("MainViewModel", "Correctement récupérés :  ${fetchedMaps.size} agents.")
                Log.d("MainViewModel", "Premiere Map récupérée : ${fetchedMaps.firstOrNull()}")
            } catch (e: Exception) {
                // S'il y a une erreur, on l'affiche aussi
                Log.e("MainViewModel", "Une erreur s'est produite lors du fetch des maps: ", e)
            }
        }
    }

    fun getMapByUuid(uuid: String): Flow<Map?> {
        return maps.map { mapList ->
            mapList.find { it.uuid == uuid }
        }
    }
}
