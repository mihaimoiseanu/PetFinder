package com.example.petfinder.networking

import com.example.petfinder.networking.models.AccessTokenResponse
import com.example.petfinder.networking.models.AnimalsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PetFinderAPI {

    @FormUrlEncoded
    @POST("v2/oauth2/token")
    fun getAccessToken(
        @Field("grant_type")
        grantType: String = "client_credentials",
        @Field("client_id")
        clientId: String,
        @Field("client_secret")
        clientSecret: String
    ): Single<AccessTokenResponse>

    @GET("v2/animals")
    fun getPets(
        @Query("page")
        page: Int
    ): Single<AnimalsResponse>

}