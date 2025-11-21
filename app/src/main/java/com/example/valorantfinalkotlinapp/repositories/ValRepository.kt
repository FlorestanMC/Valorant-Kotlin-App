package com.example.valorantfinalkotlinapp.repositories

import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.models.ApiResponseAgent
import com.example.valorantfinalkotlinapp.models.ApiResponseMap
import com.example.valorantfinalkotlinapp.models.Map
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

    /**
     * Récupère la liste des agents jouables depuis l'API Valorant.
     */
    suspend fun getAgents(): List<Agent> {
        val url = "https://valorant-api.com/v1/agents?isPlayableCharacter=true"
        val response = client.request(url).body<ApiResponseAgent>()
        return response.data
    }

    /**
     * Récupère la liste des maps depuis l'API Valorant, en filtrant celles qui n'ont pas d'image.
     */
    suspend fun getMaps(): List<Map> {
        val url = "https://valorant-api.com/v1/maps"
        val response = client.request(url).body<ApiResponseMap>()
        // On ne garde que les cartes qui ont une image à afficher
        return response.data.filter { it.splash != null }
    }
}
