package com.example.valorantfinalkotlinapp.models

import kotlinx.serialization.Serializable

// Classe pour la réponse globale de l'API des cartes
@Serializable
data class ApiResponseMap(
    val status: Int,    val data: List<Map>
)

// Classe pour UNE SEULE carte, avec les infos directement dedans
@Serializable
data class Map(
    val uuid: String,
    val displayName: String,
    val narrativeDescription: String? = null, // Description pour le dos de la carte
    val splash: String? = null,
    val displayIcon: String? = null
)