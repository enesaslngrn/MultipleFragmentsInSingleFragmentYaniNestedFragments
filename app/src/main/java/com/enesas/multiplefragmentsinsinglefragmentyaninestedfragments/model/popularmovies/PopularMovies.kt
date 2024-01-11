package com.enesas.movieapp.model.popularmovies

import com.google.gson.annotations.SerializedName

data class PopularMovies(
    val page: Int?,
    val results: MutableList<ResultPopularMovies?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

