package com.example.petfinder

import com.example.petfinder.networking.models.Breeds
import com.example.petfinder.networking.models.RemoteAnimal

class PetFactory {

    private var currentId = 0
    fun createPet(): RemoteAnimal {
        return RemoteAnimal(
            id = ++currentId,
            name = "Pet $currentId",
            gender = "",
            size = "",
            status = "",
            distance = null,
            breeds = Breeds("", null, false, false),
            photos = emptyList()
        )
    }
}