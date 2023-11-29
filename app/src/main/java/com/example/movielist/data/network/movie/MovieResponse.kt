package com.example.movielist.data.network.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("text_list")
    val actors: List<MovieResponseActorItem>,
    @SerializedName("size") //the API has no "budget" field
    val budget: Int?,
    @SerializedName("price") //the API has no "rating" field
    val rating: Double?,
    @SerializedName("date")
    val releaseDate: String?
)