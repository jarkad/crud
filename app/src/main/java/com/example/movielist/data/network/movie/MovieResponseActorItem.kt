package com.example.movielist.data.network.movie

import com.google.gson.annotations.SerializedName

data class MovieResponseActorItem(
    @SerializedName("id")
    val actorEntryId: String,
    @SerializedName("record_id")
    val movieRecordId: String,
    @SerializedName("value")
    val actorName: String
)