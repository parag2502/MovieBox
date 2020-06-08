package com.xebia.moviebox.repositories

import android.util.Log
import com.xebia.moviebox.models.MovieDetail
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.repositories.remote.MovieService
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val movieService: MovieService
) {

    fun getNowPlayingMovies(): Observable<NowPlayingMovie> {
        return movieService.getNowPlayingMovieData()
            .doOnNext {
                Log.e("ResponseNowPlaying API", "${it}");
            }
    }

    fun getMovieDetail(movieId: Int): Observable<MovieDetail> {
        return movieService.getMovieDetailData(movieId)
            .doOnNext {
                Log.e("ResponseMovieDetail API", "${it}");
            }
    }
}