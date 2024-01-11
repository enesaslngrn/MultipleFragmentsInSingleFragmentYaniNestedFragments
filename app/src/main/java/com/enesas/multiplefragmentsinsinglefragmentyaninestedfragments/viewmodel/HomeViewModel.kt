package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesas.movieapp.model.popularmovies.PopularMovies
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel (val homeRepository: HomeRepository) : ViewModel() {

    val popularMovies : MutableLiveData<Resource<PopularMovies>> = MutableLiveData()
    var popularMoviesPage = 1
    var popularMoviesResponse : PopularMovies? = null




    fun getPopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            popularMovies.postValue(Resource.Loading())
            val response = homeRepository.getPopularMovies(popularMoviesPage)
            popularMovies.postValue(handlePopularMoviesResponse(response))
        }
    }



    private fun handlePopularMoviesResponse(response: Response<PopularMovies>): Resource<PopularMovies> {
        if (response.isSuccessful){
            response.body()?.let {
                popularMoviesPage++

                if (popularMoviesResponse == null){
                    popularMoviesResponse = it

                    it.results?.forEach {
                        viewModelScope.launch(Dispatchers.IO) {
                            homeRepository.deleteAllTrending()
                            if (it != null) {
                                homeRepository.insertAllTrending(it)
                            }
                        }
                    }
                }else{
                    val oldPopularMovies = popularMoviesResponse?.results
                    val newPopularMovies = it.results
                    if (newPopularMovies != null) {
                        oldPopularMovies?.addAll(newPopularMovies)
                    }

                    newPopularMovies?.forEach {
                        viewModelScope.launch(Dispatchers.IO) {
                            if (it != null) {
                                homeRepository.insertAllTrending(it)
                            }
                        }
                    }
                }
                return Resource.Success(popularMoviesResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}