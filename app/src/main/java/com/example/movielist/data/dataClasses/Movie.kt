package com.example.movielist.data.dataClasses

data class Movie(
    val id: String = "",
    val name: String,
    val description: String?,
    val actors: List<String>? = emptyList(),
    val budget: Int? = null,
    val rating: Double? = null,
    val releaseDate: String? = null
)
