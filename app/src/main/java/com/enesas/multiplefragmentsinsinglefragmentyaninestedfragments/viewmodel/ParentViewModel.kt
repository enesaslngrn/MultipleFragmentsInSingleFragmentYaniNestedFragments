package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesas.movieapp.model.popularmovies.ResultPopularMovies
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository
import kotlinx.coroutines.launch

class ParentViewModel (val homeRepository: HomeRepository) : ViewModel() {

    val trendingMovieDetailsLiveData = MutableLiveData<ResultPopularMovies>()

    fun getRoomMovieData(movieId: Int) {

        viewModelScope.launch {

            val trendingMovie = homeRepository.getTrendingMovie(movieId)
            trendingMovieDetailsLiveData.value = trendingMovie
        }
    }
}