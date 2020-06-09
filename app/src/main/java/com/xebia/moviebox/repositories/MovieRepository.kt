package com.xebia.moviebox.repositories

import android.util.Log
import com.xebia.moviebox.models.MovieDetail
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.repositories.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    /**
     * Function to get the data of now playing movies
     */
    fun getNowPlayingMovies(): Observable<NowPlayingMovie> {
        return movieService.getNowPlayingMovieData()
            .doOnNext {
                Log.v("ResponseNowPlaying API", "${it}");
            }
    }

    /**
     * Function to get the data of most popular movies
     */
    fun getMostPopularMovies(page: Int): Observable<PopularMovie> {
        return movieService.getPopularMovieData(page)
            .doOnNext {
                Log.v("ResponsePopular API", "${it}");
            }
    }

    /**
     * Function to get the details of movie
     */
    fun getMovieDetail(movieId: Int): Observable<MovieDetail> {
        return movieService.getMovieDetailData(movieId)
            .doOnNext {
                Log.v("ResponseMovieDetail API", "${it}");
            }
    }
}