package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository

import com.enesas.movieapp.database.MovieDAODatabase

import com.enesas.movieapp.model.popularmovies.PopularMovies
import com.enesas.movieapp.model.popularmovies.ResultPopularMovies

import com.enesas.movieapp.service.MovieAPIService
import retrofit2.Response

class HomeRepository (
    val db: MovieDAODatabase
) {

    suspend fun getPopularMovies(page : Int) : Response<PopularMovies> =
        MovieAPIService.api.getPopularMovies(page)

    suspend fun insertAllTrending(allPopularMovieIds: ResultPopularMovies) = db.movieDao().insertAllTrending(allPopularMovieIds)
    fun getAllTrending() = db.movieDao().getAllTrending()
    suspend fun getTrendingMovie(singlePopularMovieId : Int) = db.movieDao().getTrendingMovie(singlePopularMovieId)
    suspend fun deleteAllTrending() = db.movieDao().deleteAllTrending()




}