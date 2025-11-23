package com.example.valorantfinalkotlinapp.repositories

import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.models.ApiResponseAgent
import com.example.valorantfinalkotlinapp.models.ApiResponseContentTiers
import com.example.valorantfinalkotlinapp.models.ApiResponseMap
import com.example.valorantfinalkotlinapp.models.ApiResponseSkins
import com.example.valorantfinalkotlinapp.models.ApiResponseThemes
import com.example.valorantfinalkotlinapp.models.ApiResponseWeapons
import com.example.valorantfinalkotlinapp.models.ContentTier
import com.example.valorantfinalkotlinapp.models.Map
import com.example.valorantfinalkotlinapp.models.Skin
import com.example.valorantfinalkotlinapp.models.Theme
import com.example.valorantfinalkotlinapp.models.Weapon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ValRepository {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private suspend fun getWeapons(): List<Weapon> {
        val url = "https://valorant-api.com/v1/weapons"
        val response = client.request(url).body<ApiResponseWeapons>()
        return response.data
    }

    private suspend fun getThemes(): List<Theme> {
        val url = "https://valorant-api.com/v1/themes"
        val response = client.request(url).body<ApiResponseThemes>()
        return response.data
    }

    suspend fun getSkins(): List<Skin> {
        val weapons = getWeapons()
        val themes = getThemes().associateBy { it.uuid }

        val allSkins = mutableListOf<Skin>()

        for (weapon in weapons) {
            for (skin in weapon.skins) {
                if (skin.displayIcon != null) {
                    val enrichedSkin = skin.copy(
                        weaponDisplayName = weapon.displayName,
                        themeDisplayName = themes[skin.themeUuid]?.displayName ?: "Standard"
                    )
                    allSkins.add(enrichedSkin)
                }
            }
        }
        return allSkins
    }

    suspend fun getAgents(): List<Agent> {
        val url = "https://valorant-api.com/v1/agents?isPlayableCharacter=true"
        val response = client.request(url).body<ApiResponseAgent>()
        return response.data
    }

    suspend fun getMaps(): List<Map> {
        val url = "https://valorant-api.com/v1/maps"
        val response = client.request(url).body<ApiResponseMap>()
        return response.data.filter { it.splash != null }
    }

    suspend fun getContentTiers(): List<ContentTier> {
        val url = "https://valorant-api.com/v1/contenttiers"
        val response = client.request(url).body<ApiResponseContentTiers>()
        return response.data
    }
}
