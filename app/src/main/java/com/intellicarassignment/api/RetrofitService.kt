package com.graphicalab.api


import com.intellicarassignment.models.movie.MovieDetailResponse
import com.intellicarassignment.models.search.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/")
    fun searchMovies(@Query("s") keyword: String, @Query("apikey") apiKey: String): Call<SearchResponse>

    @GET("/")
    fun getMovieDetails(@Query("i") imdbId: String, @Query("apikey") apiKey: String): Call<MovieDetailResponse>


}