package com.example.movielist.data.network

import com.example.movielist.data.network.movie.MovieRequest
import com.example.movielist.data.network.movie.MovieResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("records/all")
    suspend fun getAllMovies(
        @Query("student_id") student_id: String
    ): MyListResponse<MovieResponse>

    @POST("records")
    suspend fun insertNewMovie(
        @Query("student_id") student_id: String,
        @Body movieRequest: MovieRequest
    ): MyResponse

    @GET("records/{record_id}")
    suspend fun getOneMovieById(
        @Path("record_id") record_id: String,
        @Query("student_id") student_id: String
    ): MyItemResponse<MovieResponse>
}