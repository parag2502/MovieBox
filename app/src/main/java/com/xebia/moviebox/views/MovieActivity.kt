package com.xebia.moviebox.views

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xebia.moviebox.R
import com.xebia.moviebox.adapters.MostPopularMovieAdapter
import com.xebia.moviebox.adapters.NowPlayingMovieAdapter
import com.xebia.moviebox.custom.EndlessRecyclerView
import com.xebia.moviebox.interfaces.OnLoadMoreListener
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.viewmodels.MovieViewModel
import com.xebia.moviebox.viewmodels.factory.MovieViewModelFactory
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    var popularMovies: ArrayList<PopularMovie.Result> = ArrayList();
    var page: Int = 1
    var pageSize: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val context: Context = this
        AndroidInjection.inject(this)

        val rvNowPlayingMovies = findViewById<RecyclerView>(R.id.rv_playing_now)
        rvNowPlayingMovies.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val nowPlayingMovieAdapter =
            NowPlayingMovieAdapter(this)
        rvNowPlayingMovies.adapter = nowPlayingMovieAdapter

        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(
            MovieViewModel::class.java
        )

        val rvPopularMovies = findViewById<RecyclerView>(R.id.rv_most_popular)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val popularMovieAdapter = MostPopularMovieAdapter(this)
        rvPopularMovies.adapter = popularMovieAdapter
        rvPopularMovies.layoutManager = layoutManager
        var scrollListener = EndlessRecyclerView(layoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                if (page <= pageSize)
                    loadMorePopularMovies()
            }
        })
        rvPopularMovies.addOnScrollListener(scrollListener)

        movieViewModel.loadNowPlayingMovies()
        movieViewModel.loadMostPopularMovies(page)

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

        movieViewModel.mostPopularMoviesResponse().observe(this,
            Observer<PopularMovie> {
                pageSize = it.totalPages
                for (item in it.results) {
                    popularMovies.add(item)
                }
                popularMovieAdapter.setMovieData(popularMovies)
            })

        movieViewModel.mostPopularMoviesError().observe(this, Observer<String> {
            Toast.makeText(this, getString(R.string.error_something_went_wrong), Toast.LENGTH_LONG)
                .show()
        })
    }

    fun loadMorePopularMovies() {
        page = page + 1
        movieViewModel.loadMostPopularMovies(page)
    }

    override fun onDestroy() {
        super.onDestroy()
        page = 1
    }
}
