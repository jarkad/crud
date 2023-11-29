package com.example.movielist.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
	private val repository: MovieRepository
) : ViewModel() {
	val movies by lazy { MutableLiveData<List<Movie>>() }

	init {
		viewModelScope.launch {
			movies.value = repository.getMovieList()
		}
	}
}
