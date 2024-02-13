package com.example.petfinder.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        EntityAnimal::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao

    companion object {
        fun create(context: Context, useInMemory: Boolean = false): AnimalDatabase {
            return if (useInMemory) {
                Room
                    .inMemoryDatabaseBuilder(context, AnimalDatabase::class.java)
                    .build()
            } else {
                Room
                    .databaseBuilder(context, AnimalDatabase::class.java, "animal_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

}