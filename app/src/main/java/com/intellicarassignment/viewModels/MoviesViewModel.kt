package com.intellicarassignment.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.graphicalab.viewModels.BaseViewModel
import com.intellicarassignment.models.movie.MovieDetailResponse
import com.intellicarassignment.models.search.SearchData
import com.intellicarassignment.models.search.SearchResponse
import com.intellicarassignment.repositories.MovieRepository

class MoviesViewModel(appplication: Application): BaseViewModel(appplication), ApiHelper.ApiResponseListener {

    private val movieList = MutableLiveData<List<SearchData>>()
    private val movieDetail = MutableLiveData<MovieDetailResponse>()

    fun observeMovieList() = movieList
    fun observeMovieDetail() = movieDetail



    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false

        when(method){

            ApiMethods.SEARCH_MOVIES -> {

                val searchResponse: SearchResponse = response as SearchResponse
                if(searchResponse.response.equals("True")){
                    movieList.value = searchResponse.search
                    toastMessage.value = "Total results: ${searchResponse.totalResults}"
                }else{
                    toastMessage.value = searchResponse.error
                }
            }

            ApiMethods.MOVIE_DETAILS -> {
                val movieDetailResponse: MovieDetailResponse = response as MovieDetailResponse
                if(movieDetailResponse.Response.toString().equals("True")){
                    movieDetail.value = movieDetailResponse
                }else{
                    toastMessage.value = movieDetailResponse.error
                }
            }
        }

    }

    override fun onFailure(message: String?) {
        dataLoading.value = false
        toastMessage.value = message
    }

    fun searchMovies(keyword: String){

        dataLoading.value = true
        MovieRepository.getInstance().searchMovies(keyword, this)

    }

    fun getMovieDetail(imdbId: String){

        dataLoading.value = true
        MovieRepository.getInstance().getMovieDetails(imdbId, this)

    }

}