package com.example.valorantfinalkotlinapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.repositories.ValRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = ValRepository()
    val agents = MutableStateFlow<List<Agent>>(emptyList()) // état pour la liste des agents

    init {
        getAgents() // On récupère les agents dès la création du ViewModel
    }

    fun getAgents() {
        viewModelScope.launch {
            try {
                // On appelle la nouvelle fonction au pluriel
                val fetchedAgents = repository.getAgents()
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
}
