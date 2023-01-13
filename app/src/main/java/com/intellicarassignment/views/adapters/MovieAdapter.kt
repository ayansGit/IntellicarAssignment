package com.intellicarassignment.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.intellicarassignment.databinding.AdapterItemMoviesBinding
import com.intellicarassignment.models.search.SearchData

class MovieAdapter(private val movieList: ArrayList<SearchData>, val onMovieClick : (searchData: SearchData) -> Unit): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: AdapterItemMoviesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = AdapterItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(movieList[position]){
            with(holder){
                binding.ivThumb.setOnClickListener {
                    onMovieClick(movieList[position])
                }
                Glide.with(holder.itemView.context).load(poster).apply(RequestOptions.bitmapTransform( RoundedCorners(25))).into(binding.ivThumb)
                binding.tvName.text = title
                binding.tvYear.text = year
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}