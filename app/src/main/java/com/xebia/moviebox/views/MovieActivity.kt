package com.xebia.moviebox.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xebia.moviebox.NowPlayingMovieAdapter
import com.xebia.moviebox.R
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.viewmodels.MovieViewModel
import com.xebia.moviebox.viewmodels.factory.MovieViewModelFactory
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        AndroidInjection.inject(this)

        val rvNowPlayingMovies = findViewById<RecyclerView>(R.id.rv_playing_now)
        rvNowPlayingMovies.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val nowPlayingMovieAdapter = NowPlayingMovieAdapter(this)
        rvNowPlayingMovies.adapter = nowPlayingMovieAdapter

        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(
            MovieViewModel::class.java
        )

        movieViewModel.loadNowPlayingMovies()

        movieViewModel.nowPlayingMoviesResponse().observe(this,
            Observer<NowPlayingMovie> {
                var nowPlayingMovies: ArrayList<NowPlayingMovie.Result> = ArrayList();
                for (item in it.results) {
                    nowPlayingMovies.add(item)
                }
                nowPlayingMovieAdapter.setMovieData(nowPlayingMovies)
            })

        movieViewModel.nowPlayingMoviesError().observe(this, Observer<String> {
            Toast.makeText(this, getString(R.string.error_something_went_wrong), Toast.LENGTH_LONG)
                .show()
        })
    }
}
