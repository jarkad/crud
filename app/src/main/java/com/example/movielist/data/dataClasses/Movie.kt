package com.example.movielist.data.dataClasses

import com.google.firebase.firestore.DocumentId

data class Movie(
	@DocumentId val id: String = "",
	val name: String = "",
	val description: String = "",
	val actors: List<String> = emptyList(),
	val budget: Int = 0,
	val rating: Double = 0.0,
	val releaseDate: String = "",
)
