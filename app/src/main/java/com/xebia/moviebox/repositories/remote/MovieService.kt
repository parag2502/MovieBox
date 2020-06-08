package com.xebia.moviebox.repositories.remote

import com.xebia.moviebox.models.MovieDetail
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.utils.AppConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    fun getNowPlayingMovieData(
        @Query("api_key") appId: String = AppConstants.TMDB_API_KEY,
        @Query("language") lang: String = AppConstants.LANGUAGE
    ): Observable<NowPlayingMovie>

    @GET("movie/popular")
    fun getPopularMovieData(
        @Query("api_key") appId: String = AppConstants.TMDB_API_KEY,
        @Query("language") lang: String = AppConstants.LANGUAGE,
        @Query("page") page: String
    ): Observable<PopularMovie>

    @GET("movie/{movie_id}")
    fun getMovieDetailData(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appId: String = AppConstants.TMDB_API_KEY
    ): Observable<MovieDetail>
}