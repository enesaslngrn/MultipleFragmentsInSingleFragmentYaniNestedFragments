package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository

class HomeViewModelProviderFactory (val homeRepository: HomeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}