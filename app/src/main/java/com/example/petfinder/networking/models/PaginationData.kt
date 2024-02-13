package com.example.petfinder.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginationData(
    val countPerPage: Int,
    val totalCount: Int,
    val currentPage: Int,
    val totalPages: Int,
) {
    val isLastPage: Boolean
        get() = currentPage == totalPages
}