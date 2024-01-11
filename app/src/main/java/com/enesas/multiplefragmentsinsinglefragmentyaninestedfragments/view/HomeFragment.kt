package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesas.movieapp.database.MovieDAODatabase
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.adapters.HomeTrendingRecyclerAdapter
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.databinding.FragmentHomeBinding
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.util.Constants.Companion.QUERY_PAGE_SIZE
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.util.Resource
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.HomeViewModel
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.HomeViewModelProviderFactory


class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
    private var homeTrendingRecyclerAdapter = HomeTrendingRecyclerAdapter()
    private lateinit var viewModel: HomeViewModel

    val TAG = "HomeFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFragmentTrendingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.homeFragmentTrendingRecyclerView.adapter = homeTrendingRecyclerAdapter


        val homeRepository = HomeRepository(MovieDAODatabase(requireContext()))
        val viewModelProviderFactory = HomeViewModelProviderFactory(homeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)

        setupRecyclerView()
        viewModel.getPopularMovies()

        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    binding.homeFragmentTrendingProgressBar.visibility = View.INVISIBLE
                    isLoading = false
                    response.data?.let {
                        homeTrendingRecyclerAdapter.differ.submitList(it.results?.toList())
                        val totalPages = it.totalResults!! / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.popularMoviesPage == totalPages
                    }
                }

                is Resource.Error -> {
                    binding.homeFragmentTrendingProgressBar.visibility = View.INVISIBLE
                    isLoading = false
                    response.message?.let { error ->
                        Log.e(TAG, "An error occured!: $error")
                    }
                }

                is Resource.Loading -> {
                    binding.homeFragmentTrendingProgressBar.visibility = View.VISIBLE
                    isLoading = true
                }
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val popularMoviesScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLasPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLasPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible
                    && isScrolling

            if (shouldPaginate){
                viewModel.getPopularMovies()
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.homeFragmentTrendingRecyclerView.apply {
            addOnScrollListener(this@HomeFragment.popularMoviesScrollListener)
        }
    }
}