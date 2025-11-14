package com.example.valorantfinalkotlinapp.models

import kotlinx.serialization.Serializable

// Classe qui correspond à la structure racine de la réponse JSON de l'API
@Serializable
data class ApiResponse(
    val status: Int,
    val data: List<Agent>
)

@Serializable
data class Agent(
    val uuid: String,
    val displayName: String,
    val description: String,
    val displayIcon: String,
    val fullPortrait: String? = null, // Peut être nul
    val role: AgentRole? = null,         // Peut être nul
    val abilities: List<AgentAbility> = emptyList()
)

@Serializable
data class AgentRole(
    val uuid: String,
    val displayName: String,
    val description: String,
    val displayIcon: String
)

@Serializable
data class AgentAbility(
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String? = null // Peut être nul
)
