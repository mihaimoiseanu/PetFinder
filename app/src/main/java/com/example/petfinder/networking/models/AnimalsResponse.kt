package com.example.petfinder.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class AnimalsResponse(
    val animals: List<RemoteAnimal>,
    val pagination: PaginationData
)

@Serializable
data class RemoteAnimal(
    val id: Int,
    val name: String,
    val gender: String,
    val size: String,
    val status: String,
    val distance: Float?,
    val breeds: Breeds,
    val photos: List<Photo>
)

@Serializable
data class Breeds(
    val primary: String,
    val secondary: String?,
    val mixed: Boolean,
    val unknown: Boolean
)

@Serializable
data class Photo(
    val small: String?,
    val medium: String?,
    val large: String?,
    val full: String?
)

