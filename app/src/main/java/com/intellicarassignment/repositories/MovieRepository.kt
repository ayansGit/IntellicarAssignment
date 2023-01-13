package com.intellicarassignment.repositories

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.intellicarassignment.models.movie.MovieDetailResponse
import com.intellicarassignment.models.search.SearchResponse
import com.intellicarassignment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {

    fun searchMovies(keyword: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().searchMovies(keyword, Constants.API_KEY).enqueue(object :
            Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.SEARCH_MOVIES)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }
        })
    }

    fun getMovieDetails(imdbId: String, apiResponseListener: ApiHelper.ApiResponseListener){
        ApiHelper.RestService.getInstance().getMovieDetails(imdbId, Constants.API_KEY).enqueue(
            object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.code() == 200)
                        apiResponseListener.onSuccess(response.body(), ApiMethods.MOVIE_DETAILS)
                    else apiResponseListener.onFailure(response.message())
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    apiResponseListener.onFailure(t.message)
                }

            })
    }

    companion object {
        private var INSTANCE: MovieRepository? = null
        fun getInstance() = INSTANCE
            ?: MovieRepository().also {
                INSTANCE = it
            }
    }
}