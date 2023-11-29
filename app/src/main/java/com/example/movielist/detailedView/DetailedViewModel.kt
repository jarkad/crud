package com.example.movielist.detailedView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import kotlinx.coroutines.launch

class DetailedViewModel(
    movieId: String,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieLiveData: MutableLiveData<Movie> by lazy {
        MutableLiveData<Movie>()
    }

    init {
        getMovieById(movieId)
    }

    private fun getMovieById(movieId: String) {
        viewModelScope.launch {
            if (!movieId.isNullOrEmpty()) {
                val movie = movieRepository.getMovieById(movieId)
                movieLiveData.value = movie
            }
        }
    }

}