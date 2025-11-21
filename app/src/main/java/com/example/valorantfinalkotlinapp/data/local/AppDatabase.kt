package com.example.valorantfinalkotlinapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameStat::class], version = 2, exportSchema = false) // On passe à la version 2
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameStatDao(): GameStatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration() // On gère la migration
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
