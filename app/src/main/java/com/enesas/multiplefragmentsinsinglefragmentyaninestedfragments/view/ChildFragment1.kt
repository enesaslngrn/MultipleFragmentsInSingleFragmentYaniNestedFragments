package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enesas.movieapp.database.MovieDAODatabase
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.databinding.FragmentChild1Binding
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.ParentViewModel
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.ParentViewModelProviderFactory


class ChildFragment1 (private val movieId : Int): Fragment() {

    private lateinit var binding: FragmentChild1Binding
    private lateinit var viewModel: ParentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChild1Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeRepository = HomeRepository(MovieDAODatabase(requireContext()))
        val viewModelProviderFactory = ParentViewModelProviderFactory(homeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ParentViewModel::class.java)

        viewModel.getRoomMovieData(movieId)

        observeLiveData()
    }

    fun observeLiveData() {

        viewModel.trendingMovieDetailsLiveData.observe(viewLifecycleOwner, Observer { results ->
            results?.let {
                binding.tvParentMovieNameC1.text = it.title
                binding.tvParentMovieRateC1.text = it.voteAverage.toString()
            }
        })
    }
}