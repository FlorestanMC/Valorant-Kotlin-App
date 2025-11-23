package com.example.valorantfinalkotlinapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.valorantfinalkotlinapp.models.Skin
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinDao {

    @Query("SELECT * FROM skins")
    fun getAllSkins(): Flow<List<Skin>>

    @Query("SELECT * FROM skins WHERE isOwned = 1")
    fun getOwnedSkins(): Flow<List<Skin>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(skins: List<Skin>)

    @Update
    suspend fun updateSkin(skin: Skin)

    @Query("SELECT COUNT(uuid) FROM skins")
    suspend fun getSkinCount(): Int
}
