package com.enesas.movieapp.service

import com.enesas.movieapp.model.popularmovies.PopularMovies
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    // "https://api.themoviedb.org/3/trending/movie/day?language=tr-1974"

    @GET("trending/movie/day?language=tr-1974")
    suspend fun getPopularMovies(
        @Query("page") page : Int = 1,
        @Query("api_key") api_key : String = API_KEY
    ): Response<PopularMovies>

}