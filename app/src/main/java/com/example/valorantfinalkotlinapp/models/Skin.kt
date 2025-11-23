package com.example.valorantfinalkotlinapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

// Structures pour l'API
@Serializable
data class ApiResponseWeapons(
    val status: Int,
    val data: List<Weapon>
)

@Serializable
data class Weapon(
    val uuid: String,
    val displayName: String,
    val skins: List<Skin>
)

@Serializable
data class ApiResponseThemes(
    val status: Int,
    val data: List<Theme>
)

@Serializable
data class Theme(
    val uuid: String,
    val displayName: String
)


// Entité pour la base de données
@Entity(tableName = "skins")
@Serializable
data class Skin(
    @PrimaryKey val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val contentTierUuid: String?,
    val themeUuid: String? = null, // Ajout pour la collection
    var weaponDisplayName: String? = null, // Ajout pour l'arme
    var themeDisplayName: String? = null, // Ajout pour le nom de la collection
    var isOwned: Boolean = false
)

// Ancien modèle, conservé pour la compatibilité avec l'ouverture de boîtes
@Serializable
data class ApiResponseSkins(
    val status: Int,
    val data: List<Skin>
)

@Serializable
data class ApiResponseContentTiers(
    val status: Int,
    val data: List<ContentTier>
)

@Serializable
data class ContentTier(
    val uuid: String,
    val displayName: String,
    val highlightColor: String?,
    val juiceCost: Int?, // Note: Peut ne pas être un prix de marché direct
    val displayIcon: String?
)
