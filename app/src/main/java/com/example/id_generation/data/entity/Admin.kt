package com.example.id_generation.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Admin")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String
)
