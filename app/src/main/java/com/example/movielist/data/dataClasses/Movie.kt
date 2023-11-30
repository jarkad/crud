package com.example.movielist.data.dataClasses

import com.google.firebase.firestore.DocumentId

data class Movie(
	@DocumentId val id: String = "",
	val name: String = "",
	val description: String = "",
	val price: Int = 0,
	val rating: Double = 0.0,
	val category: String = "",
	val color: String = "",
	val condition: String = ""
)
