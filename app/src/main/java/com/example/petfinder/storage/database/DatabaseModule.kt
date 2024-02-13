package com.example.petfinder.storage.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAnimalDatabase(@ApplicationContext context: Context): AnimalDatabase {
        return AnimalDatabase.create(context)
    }

    @Singleton
    @Provides
    fun provideAnimalDao(animalDatabase: AnimalDatabase): AnimalDao = animalDatabase.animalDao()
}