package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enesas.movieapp.database.MovieDAODatabase
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.R
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.databinding.FragmentParentBinding
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.repository.HomeRepository
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.ParentViewModel
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.viewmodel.ParentViewModelProviderFactory

class ParentFragment : Fragment() {
    private lateinit var binding: FragmentParentBinding
    private lateinit var viewModel: ParentViewModel
    private var movieId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movieId = ParentFragmentArgs.fromBundle(it).movieId
        }

        val homeRepository = HomeRepository(MovieDAODatabase(requireContext()))
        val viewModelProviderFactory = ParentViewModelProviderFactory(homeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ParentViewModel::class.java)

        viewModel.getRoomMovieData(movieId)

        childFragmentManager.beginTransaction().replace(R.id.flParent, ChildFragment1(movieId)).commit() // Varsayılan olarak gösterilen child1 olacak.
        setupButtonListeners(view)

        observeLiveData()

    }

    fun observeLiveData() {

        viewModel.trendingMovieDetailsLiveData.observe(viewLifecycleOwner, Observer { results ->
            results?.let {
                binding.tvParentMovieName.text = it.title
                binding.tvParentMovieRate.text = it.voteAverage.toString()
            }
        })
    }

    private fun setupButtonListeners(view: View){
        binding.btnChild1.setOnClickListener {
            showFragment(ChildFragment1(movieId))
        }
        binding.btnChild2.setOnClickListener {
            showFragment(ChildFragment2(movieId))
        }
        binding.btnChild3.setOnClickListener {
            showFragment(ChildFragment3(movieId))
        }
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.flParent, fragment).commit()
    }

}