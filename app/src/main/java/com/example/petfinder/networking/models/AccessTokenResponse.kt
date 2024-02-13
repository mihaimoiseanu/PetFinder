package com.example.petfinder.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val tokenType: String,
    val expiresIn: Int,
    val accessToken: String
)
