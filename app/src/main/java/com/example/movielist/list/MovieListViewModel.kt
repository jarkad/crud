package com.example.movielist.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val moviesLiveData: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            val movies = movieRepository.getMovieList()
            moviesLiveData.value = movies
        }
    }
}