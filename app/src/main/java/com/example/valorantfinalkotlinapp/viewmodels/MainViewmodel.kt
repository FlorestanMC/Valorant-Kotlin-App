package com.example.valorantfinalkotlinapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorantfinalkotlinapp.data.local.AppDatabase
import com.example.valorantfinalkotlinapp.data.local.SkinDao
import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.models.ContentTier
import com.example.valorantfinalkotlinapp.models.Map
import com.example.valorantfinalkotlinapp.models.Skin
import com.example.valorantfinalkotlinapp.repositories.ValRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val agents = MutableStateFlow<List<Agent>>(emptyList()) // état pour la liste des agents
    val maps = MutableStateFlow<List<Map>>(emptyList()) // état pour la liste des maps

    private val _contentTiers = MutableStateFlow<List<ContentTier>>(emptyList())
    val contentTiers = _contentTiers.asStateFlow()

    private val skinDao: SkinDao

    val allSkins: StateFlow<List<Skin>>
    val ownedSkins: StateFlow<List<Skin>>

    init {
        val context = getApplication<Application>().applicationContext
        skinDao = AppDatabase.getDatabase(context).skinDao()

        getAgents()
        getMaps()
        fetchSkinsIfNeeded()
        getContentTiers()

        allSkins = skinDao.getAllSkins()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        ownedSkins = skinDao.getOwnedSkins()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    private fun fetchSkinsIfNeeded() {
        viewModelScope.launch {
            if (skinDao.getSkinCount() == 0) {
                try {
                    val fetchedSkins = ValRepository.getSkins()
                    skinDao.insertAll(fetchedSkins)
                    Log.d("MainViewModel", "Skins fetched and inserted into database.")
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error fetching skins: ", e)
                }
            }
        }
    }

    fun updateSkin(skin: Skin) {
        viewModelScope.launch {
            skinDao.updateSkin(skin)
        }
    }

    fun getAgents() {
        viewModelScope.launch {
            try {
                val fetchedAgents = ValRepository.getAgents()
                agents.value = fetchedAgents
                Log.d("MainViewModel", "Correctement récupérés :  ${fetchedAgents.size} agents.")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Une erreur s\'est produite lors du fetch des agents: ", e)
            }
        }
    }

    fun getContentTiers() {
        viewModelScope.launch {
            try {
                _contentTiers.value = ValRepository.getContentTiers()
                Log.d("MainViewModel", "Content tiers fetched successfully.")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching content tiers: ", e)
            }
        }
    }

    fun getMaps() {
        viewModelScope.launch {
            try {
                val fetchedMaps = ValRepository.getMaps()
                maps.value = fetchedMaps
                Log.d("MainViewModel", "Correctement récupérés :  ${fetchedMaps.size} maps.")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Une erreur s\'est produite lors du fetch des maps: ", e)
            }
        }
    }

    fun getMapByUuid(uuid: String): Flow<Map?> {
        return maps.map { mapList ->
            mapList.find { it.uuid == uuid }
        }
    }
}
