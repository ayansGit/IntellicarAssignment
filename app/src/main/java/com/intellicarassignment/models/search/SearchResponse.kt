package com.intellicarassignment.models.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") val search: ArrayList<SearchData>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String?
)