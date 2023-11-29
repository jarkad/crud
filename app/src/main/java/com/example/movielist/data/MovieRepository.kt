package com.example.movielist.data

import android.util.Log
import com.example.movielist.data.dataClasses.Movie
import com.example.movielist.data.network.MyItemResponse
import com.example.movielist.data.network.MyListResponse
import com.example.movielist.data.network.MyResponse
import com.example.movielist.data.network.RetrofitInstance
import com.example.movielist.data.network.movie.MovieRequest
import com.example.movielist.data.network.movie.MovieResponse
import com.example.movielist.data.network.movie.MovieResponseActorItem

class MovieRepository {
    suspend fun getMovieList(): List<Movie> {
        val movies = mutableListOf<Movie>()

        try {
            val response: MyListResponse<MovieResponse> =
                RetrofitInstance.movieService.getAllMovies("movie")
            val moviesFromResponse = response.data

            if (moviesFromResponse != null) {

                for (movieFromResponse in moviesFromResponse) {
                    if (movieFromResponse.description != null) {
                        movies.add(
                            Movie(
                                id = movieFromResponse.id.toString(),
                                name = movieFromResponse.name.uppercase(),
                                description = movieFromResponse.description
                            )
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return movies
    }

    suspend fun insertNewMovie(movie: Movie): MyResponse? {
        val response: MyResponse

        try {
            val movieRequest =
                MovieRequest(
                    name = movie.name,
                    description = movie.description,
                    actors = movie.actors,
                    budget = movie.budget,
                    rating = movie.rating,
                    releaseDate = movie.releaseDate
                )

            response = RetrofitInstance.movieService.insertNewMovie(
                "movie",
                movieRequest
            )

            Log.d("Update_response", response.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return response
    }

    suspend fun getMovieById(movieId: String): Movie? {
        try {
            val response: MyItemResponse<MovieResponse> =
                RetrofitInstance.movieService.getOneMovieById(movieId, "movie")
            val movieFromResponse = response.data

            if (movieFromResponse != null) {
                if (movieFromResponse.description != null
                ) {
                    return Movie(
                        id = movieId,
                        name = movieFromResponse.name,
                        description = movieFromResponse.description,
                        actors = extractListOfActorsFromResponse(movieFromResponse.actors),
                        budget = movieFromResponse.budget,
                        rating = movieFromResponse.rating,
                        releaseDate = formatReleaseDate(movieFromResponse.releaseDate)
                    )
                }
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
        return null
    }

    private fun extractListOfActorsFromResponse(
        actorsFromResponse: List<MovieResponseActorItem>
    ): List<String> {

        val myActors = mutableListOf<String>()

        for (actorObj in actorsFromResponse) {
            myActors.add(actorObj.actorName)
        }

        return myActors
    }

    private fun formatReleaseDate(releaseDate: String?): String {
        if (releaseDate.isNullOrEmpty()) return ""

        //release date will come in the format "YYYY-MM-DD HH:MM:SS". We should trim the "time" part
        //in Kotlin there is a special function for that, called "dropLast(number of characters to trim)"

        return releaseDate.dropLast(9)
    }
}

