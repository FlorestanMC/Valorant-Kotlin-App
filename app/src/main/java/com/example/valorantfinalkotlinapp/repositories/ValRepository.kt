package com.example.valorantfinalkotlinapp.repositories

import com.example.valorantfinalkotlinapp.models.Agent
import com.example.valorantfinalkotlinapp.models.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ValRepository {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true // Ajout pour être plus flexible avec le JSON
            })
        }
    }

    /**
     * Récupère la liste des agents depuis l'API Valorant.
     */
    suspend fun getAgents(): List<Agent> {
        val url = "https://valorant-api.com/v1/agents"
        // Ktor va maintenant parser la réponse en tant qu'ApiResponse
        val response = client.request(url).body<ApiResponse>()
        // On retourne uniquement la liste d'agents qui nous intéresse
        return response.data
    }

    suspend fun getMaps(): List<Map> {
        val url = "https://valorant-api.com/v1/agents"
        // Ktor va maintenant parser la réponse en tant qu'ApiResponse
        val response = client.request(url).body<ApiResponse>()
        // On retourne uniquement la liste d'agents qui nous intéresse
        return response.data
    }

    suspend fun getArmes(): List<Arme> {
        val url = "https://valorant-api.com/v1/agents"
        // Ktor va maintenant parser la réponse en tant qu'ApiResponse
        val response = client.request(url).body<ApiResponse>()
        // On retourne uniquement la liste d'agents qui nous intéresse
        return response.data
    }
}
