package com.example.petfinder.networking

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {

    var authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            if (authToken.isNullOrBlank()) request()
            else request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $authToken")
                .build()
        )
    }
}