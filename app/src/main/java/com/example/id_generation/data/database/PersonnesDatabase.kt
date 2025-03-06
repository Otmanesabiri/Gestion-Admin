package com.example.id_generation.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.id_generation.data.dao.AdminDao
import com.example.id_generation.data.entity.Admin

@Database(entities = [Admin::class], version = 1, exportSchema = false)
abstract class PersonnesDatabase : RoomDatabase() {

    abstract fun adminDao(): AdminDao

    companion object {
        @Volatile
        private var Instance: PersonnesDatabase? = null

        fun getDatabase(context: Context): PersonnesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PersonnesDatabase::class.java,
                    "Personnes.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { Instance = it }
            }
        }
    }
}
