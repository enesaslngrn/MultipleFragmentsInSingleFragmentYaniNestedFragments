package com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enesas.movieapp.model.popularmovies.ResultPopularMovies
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.databinding.HomeTrendingRecyclerRowBinding
import com.enesas.multiplefragmentsinsinglefragmentyaninestedfragments.view.HomeFragmentDirections
import com.squareup.picasso.Picasso


class HomeTrendingRecyclerAdapter : RecyclerView.Adapter<HomeTrendingRecyclerAdapter.HomeTrendingViewHolder>(){

    inner class HomeTrendingViewHolder (val binding : HomeTrendingRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ResultPopularMovies>(){
        override fun areItemsTheSame(
            oldItem: ResultPopularMovies,
            newItem: ResultPopularMovies
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultPopularMovies,
            newItem: ResultPopularMovies
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTrendingViewHolder {
        val binding = HomeTrendingRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeTrendingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HomeTrendingViewHolder, position: Int) {
        val trendingList = differ.currentList

        holder.binding.homeRecyclerRowMovieNameText.text = trendingList.get(position).title

        if (trendingList.get(position).posterPath != null && trendingList.get(position).posterPath != ""){
            val img_base_url = "https://image.tmdb.org/t/p/w500"
            val img_final_url = img_base_url + trendingList.get(position).posterPath

            Picasso.get().load(img_final_url).into(holder.binding.homeRecyclerRowImageView)
        }

        holder.itemView.setOnClickListener {
            if (trendingList.get(position).id != null){
                val action = HomeFragmentDirections.actionHomeFragmentToParentFragment(trendingList.get(position).id!!)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}