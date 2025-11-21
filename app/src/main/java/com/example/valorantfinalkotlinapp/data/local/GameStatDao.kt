package com.example.valorantfinalkotlinapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameStatDao {

    @Insert
    suspend fun insert(gameStat: GameStat)

    @Query("SELECT * FROM game_stats ORDER BY timestamp DESC")
    fun getAll(): Flow<List<GameStat>>
}
