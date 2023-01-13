package com.intellicarassignment.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.intellicarassignment.R
import com.intellicarassignment.databinding.FragmentMoviesBinding
import com.intellicarassignment.models.search.SearchData
import com.intellicarassignment.utils.Constants
import com.intellicarassignment.utils.hideKeyboard
import com.intellicarassignment.utils.show
import com.intellicarassignment.viewModels.MoviesViewModel
import com.intellicarassignment.views.activities.MovieDetailActivity
import com.intellicarassignment.views.activities.MoviesActivity
import com.intellicarassignment.views.adapters.MovieAdapter


class MoviesFragment : Fragment() {

    private val TAG = "MoviesFragment"
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private val searchData = ArrayList<SearchData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        movieAdapter = MovieAdapter(searchData){
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_IMDB_ID, it.imdbID)
            startActivity(intent)
        }
        binding.rvMovies.layoutManager = GridLayoutManager(context, 3)
        binding.rvMovies.adapter = movieAdapter

        observeMovies()
        observeLoading()
        observeToast()

        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == EditorInfo.IME_ACTION_SEARCH){
                    moviesViewModel.searchMovies(binding.etSearch.text.toString().trim())
                    hideKeyboard(binding.etSearch)
                    return true;
                }

                return false
            }
        })

    }

    private fun observeMovies(){
        moviesViewModel.observeMovieList().observe(viewLifecycleOwner) {
            searchData.clear();
            searchData.addAll(it)
            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun observeLoading(){
        moviesViewModel.observeLoading().observe(viewLifecycleOwner){
            binding.loader.show(it)
        }
    }

    private fun observeToast(){
        moviesViewModel.observeToast().observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}