package com.xebia.moviebox.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xebia.moviebox.R
import com.xebia.moviebox.adapters.MostPopularMovieAdapter
import com.xebia.moviebox.adapters.NowPlayingMovieAdapter
import com.xebia.moviebox.custom.EndlessRecyclerOnScrollListener
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.viewmodels.MovieViewModel
import com.xebia.moviebox.viewmodels.factory.MovieViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie.*
import java.util.*
import javax.inject.Inject


class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    private lateinit var nowPlayingMovieAdapter: NowPlayingMovieAdapter
    private lateinit var mostPopularMovieAdapter: MostPopularMovieAdapter
    var popularMovies: ArrayList<PopularMovie.Result> = ArrayList();
    var page: Int = 1
    var pageSize: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        AndroidInjection.inject(this)

        // Get MovieViewModel
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(
            MovieViewModel::class.java
        )
        // set now playing movies recyclerview
        setNowPlayingRecyclerView()

        // set most popular movies recyclerview
        setMostPopularRecyclerView()

        // load now playing movies from movieViewModel
        movieViewModel.loadNowPlayingMovies()

        // load most popular from movieViewModel
        movieViewModel.loadMostPopularMovies(page)

        // Handle now playing movies response
        movieViewModel.nowPlayingMoviesResponse().observe(this,
            Observer<NowPlayingMovie> {
                var nowPlayingMovies: ArrayList<NowPlayingMovie.Result> = ArrayList();
                for (item in it.results) {
                    nowPlayingMovies.add(item)
                }
                nowPlayingMovieAdapter.setMovieData(nowPlayingMovies)
            })

        // Handle now playing movies error
        movieViewModel.nowPlayingMoviesError().observe(this, Observer<String> {
            Toast.makeText(this, getString(R.string.error_something_went_wrong), Toast.LENGTH_LONG)
                .show()
        })

        // Handle most popular movies response
        movieViewModel.mostPopularMoviesResponse().observe(this,
            Observer<PopularMovie> {
                pageSize = it.totalPages
                for (item in it.results) {
                    popularMovies.add(item)
                }
                progress_bar.visibility = View.GONE
                mostPopularMovieAdapter.setMovieData(popularMovies)
            })

        // Handle most popular movies error
        movieViewModel.mostPopularMoviesError().observe(this, Observer<String> {
            Toast.makeText(this, getString(R.string.error_something_went_wrong), Toast.LENGTH_LONG)
                .show()
        })
    }

    /**
     * Function to set now playing movies recyclerview
     */
    private fun setNowPlayingRecyclerView() {
        val rvNowPlayingMovies = findViewById<RecyclerView>(R.id.rv_playing_now)
        rvNowPlayingMovies.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        nowPlayingMovieAdapter =
            NowPlayingMovieAdapter(this)
        rvNowPlayingMovies.adapter = nowPlayingMovieAdapter
    }

    /**
     * Function to set most popular movies recyclerview
     */
    private fun setMostPopularRecyclerView() {
        val rvPopularMovies = findViewById<RecyclerView>(R.id.rv_most_popular)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mostPopularMovieAdapter = MostPopularMovieAdapter(this)
        val itemDecorator =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider)!!)
        rvPopularMovies.addItemDecoration(itemDecorator)
        rvPopularMovies.adapter = mostPopularMovieAdapter
        rvPopularMovies.layoutManager = layoutManager
        rvPopularMovies.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                if (page <= pageSize)
                    loadMorePopularMovies()
            }
        })
    }

    /**
     * Function to load more popular movies
     */
    fun loadMorePopularMovies() {
        page += 1
        progress_bar.visibility = View.VISIBLE
        movieViewModel.loadMostPopularMovies(page)
    }

    override fun onDestroy() {
        super.onDestroy()
        page = 1
    }
}
