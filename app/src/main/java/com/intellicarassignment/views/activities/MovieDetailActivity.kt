package com.intellicarassignment.views.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.intellicarassignment.R
import com.intellicarassignment.databinding.ActivityMovieDetailBinding
import com.intellicarassignment.utils.Constants
import com.intellicarassignment.utils.makeStatusBarTransparent
import com.intellicarassignment.viewModels.MoviesViewModel

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(binding.parent.id)) { _, insets ->
            insets.consumeSystemWindowInsets()
        }

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        binding.btBack.setOnClickListener {
            onBackPressed()
        }

        observeMovieDetail()
        observeLoading()
        observeToast()

        intent.extras?.run {
            val imdbId = getString(Constants.EXTRA_IMDB_ID, "")
            moviesViewModel.getMovieDetail(imdbId)
        }

    }

    private fun observeMovieDetail(){
        moviesViewModel.observeMovieDetail().observe(this) { it ->
            Glide.with(this).load(it.Poster).into(binding.ivImage)
            binding.tvTitle.text = it.Title
            binding.tvTiming.text = it.Runtime
            binding.tvRating.text = it.imdbRating
            binding.tvSubTitle.text = it.Type
            binding.tvDesc.text = it.Plot
            binding.tvDirector.text = it.Director
            binding.tvWriter.text = it.Writer
            binding.tvActors.text = it.Actors
            val generes = it.Genre?.split(", ")
            binding.llGeneres.removeAllViews()
            generes?.forEach {
                val view = LayoutInflater.from(this).inflate(R.layout.layout_genere, null)
                val tvGenere = view.findViewById<TextView>(R.id.tvGenere)
                tvGenere.text = it
                binding.llGeneres.addView(view)
            }

        }
    }

    private fun observeLoading(){
        moviesViewModel.observeLoading().observe(this){

        }
    }

    private fun observeToast(){
        moviesViewModel.observeToast().observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}