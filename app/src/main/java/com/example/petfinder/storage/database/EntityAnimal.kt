package com.example.petfinder.storage.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class EntityAnimal(
    @PrimaryKey
    val id: Int,
    val name: String,
    val gender: String,
    val size: String,
    val status: String,
    val distance: Float?,
    val breeds: String,
    val image: String,
    val previewImage: String
)