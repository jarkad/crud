package com.example.movielist.addNew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import kotlinx.coroutines.launch

class AddNewMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
	fun saveNewMovie(movie: Movie) {
		viewModelScope.launch {
			movieRepository.insertNewMovie(movie)
		}
	}
}
