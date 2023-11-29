package com.example.movielist.data

import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.data.network.MyResponse
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class MovieRepository {
	private val db = Firebase.firestore
	private val collection = db.collection("movies")
	suspend fun getMovieList(): List<Movie> {
		return collection.get().await().toObjects(Movie::class.java)
	}

	suspend fun insertNewMovie(movie: Movie): MyResponse? {
		collection.add(movie).await()
		return null
	}

	suspend fun getMovieById(movieId: String): Movie? {
		return collection.document(movieId).get().await().toObject(Movie::class.java)
	}
}
