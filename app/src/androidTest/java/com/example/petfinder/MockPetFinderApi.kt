package com.example.petfinder

import com.example.petfinder.networking.PetFinderAPI
import com.example.petfinder.networking.models.AccessTokenResponse
import com.example.petfinder.networking.models.AnimalsResponse
import com.example.petfinder.networking.models.PaginationData
import com.example.petfinder.networking.models.RemoteAnimal
import io.reactivex.rxjava3.core.Single

class MockPetFinderApi : PetFinderAPI {

    private val pets = mutableListOf<RemoteAnimal>()
    var failureMsg: String? = null

    override fun getAccessToken(
        grantType: String,
        clientId: String,
        clientSecret: String
    ): Single<AccessTokenResponse> {
        TODO("Not yet implemented")
    }

    override fun getPets(page: Int): Single<AnimalsResponse> {
        return Single.just(
            AnimalsResponse(
                animals = pets,
                pagination = PaginationData(3, 3, 1, 1)
            )
        )
    }

    fun addPet(pet: RemoteAnimal) {
        pets.add(pet)
    }

    fun clearPets() {
        pets.clear()
    }
}