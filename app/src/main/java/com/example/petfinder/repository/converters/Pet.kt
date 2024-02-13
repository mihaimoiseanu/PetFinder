package com.example.petfinder.repository.converters

import com.example.petfinder.networking.models.RemoteAnimal
import com.example.petfinder.repository.models.Pet
import com.example.petfinder.storage.database.EntityAnimal

fun RemoteAnimal.toEntity(): EntityAnimal {
    return EntityAnimal(
        id = this.id,
        breeds = "${breeds.primary} - ${breeds.secondary}",
        name = name,
        gender = gender,
        size = size,
        status = status,
        distance = distance,
        image = photos.firstOrNull()?.run { full ?: large ?: medium ?: small ?: "" } ?: "",
        previewImage = photos.firstOrNull()?.run { small ?: medium ?: large ?: full ?: "" } ?: "",
    )
}

fun EntityAnimal.toDomain(): Pet {
    return Pet(
        id = id,
        name = name
    )
}