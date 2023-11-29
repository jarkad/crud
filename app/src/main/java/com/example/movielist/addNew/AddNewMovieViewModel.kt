package com.example.movielist.addNew

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.MovieRepository
import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.data.network.MyResponse
import kotlinx.coroutines.launch

class AddNewMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val insertResponseLiveData: MutableLiveData<MyResponse> by lazy {
        MutableLiveData<MyResponse>()
    }

    fun saveNewMovie(movie: Movie) {
        viewModelScope.launch {
            try {

                val response = movieRepository.insertNewMovie(movie)
                insertResponseLiveData.value = response

                Log.d("Update_response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}