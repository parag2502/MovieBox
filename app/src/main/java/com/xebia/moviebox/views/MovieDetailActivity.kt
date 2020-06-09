package com.xebia.moviebox.views

import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xebia.moviebox.R
import com.xebia.moviebox.models.MovieDetail
import com.xebia.moviebox.utils.AppConstants
import com.xebia.moviebox.utils.Util.getMovieDuration
import com.xebia.moviebox.viewmodels.DetailMovieViewModel
import com.xebia.moviebox.viewmodels.factory.MovieViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject


class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    lateinit var detailMovieViewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie_detail)

        AndroidInjection.inject(this)

        // Get Movie Id from previous activity
        val movieId: Int = intent.getIntExtra(AppConstants.KEY_MOVIE_ID, 0)

        // Handle back event
        iv_back.setOnClickListener {
            finish()
        }

        // Get DetailMovieViewModel
        detailMovieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(
            DetailMovieViewModel::class.java
        )

        // Load movie detail
        detailMovieViewModel.loadMovieDetail(movieId)

        // Handle movie detail response
        detailMovieViewModel.detailMovieResponse().observe(this,
            Observer<MovieDetail> {
                updateUI(it)
            })

        // Handle movie detail error
        detailMovieViewModel.detailMoviesError().observe(this, Observer<String> {
            Toast.makeText(this, getString(R.string.error_something_went_wrong), Toast.LENGTH_LONG)
                .show()
        })
    }

    /**
     * Function to update ui
     */
    private fun updateUI(movieDetail: MovieDetail) {
        // Set movie poster
        val posterUrl = AppConstants.MOVIE_IMAGE_URL + movieDetail.posterPath
        Glide.with(this).load(posterUrl)
            .placeholder(R.color.colorLightGray).fitCenter()
            .diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).into(iv_poster)
        // Set movie title
        tv_title.text = movieDetail?.title
        // Set movie release date
        tv_release_date_duration.text =
            "${movieDetail.releaseDate} - ${getMovieDuration(movieDetail.runtime)}"
        // Set movie overview
        tv_overview.text = movieDetail?.overview
        // Set movie genre
        for (genre in movieDetail.genres) {
            val layoutGenre =
                layoutInflater.inflate(R.layout.layout_genre_item, null) as LinearLayout
            val tvGenre = layoutGenre.findViewById(R.id.tv_genre) as TextView
            tvGenre.text = genre.name
            layout_genre.addView(layoutGenre)
        }
    }
}
