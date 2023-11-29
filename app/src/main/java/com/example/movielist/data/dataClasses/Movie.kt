package com.example.movielist.data.dataClasses

import com.google.firebase.firestore.DocumentId

data class Movie(
	@DocumentId
	val id: String = "",
	val name: String = "",
	val description: String? = null,
	val actors: List<String>? = emptyList(),
	val budget: Int? = null,
	val rating: Double? = null,
	val releaseDate: String? = null
)
