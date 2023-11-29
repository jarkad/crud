package com.example.movielist.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import kotlinx.coroutines.launch

class EditMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

	val saveSuccessfulLiveData by lazy { MutableLiveData<Boolean>() }
	val movieLiveData by lazy { MutableLiveData<Movie>() }

	fun getMovieById(movieId: String) {
		viewModelScope.launch {
			movieLiveData.value = movieRepository.getMovieById(movieId)
		}
	}

	fun save(movie: Movie) {
		viewModelScope.launch {
			try {
				movieRepository.save(movie)
				saveSuccessfulLiveData.value = true
			} catch (e: Exception) {
				e.printStackTrace()
				saveSuccessfulLiveData.value = false
			}
		}

	}
}
