package com.xebia.moviebox.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * View Model to handle the movie activity
 */
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    var nowPlayingMoviesResponse: MutableLiveData<NowPlayingMovie> = MutableLiveData()
    var nowPlayingMoviesError: MutableLiveData<String> = MutableLiveData()
    var mostPopularMoviesResponse: MutableLiveData<PopularMovie> = MutableLiveData()
    var mostPopularMoviesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserverNowPlayingMovie: DisposableObserver<NowPlayingMovie>
    lateinit var disposableObserverMostPopular: DisposableObserver<PopularMovie>

    /**
     * Function to return now playing movies response from server
     */
    fun nowPlayingMoviesResponse(): LiveData<NowPlayingMovie> {
        return nowPlayingMoviesResponse
    }

    /**
     * Function to return now playing movies error from server
     */
    fun nowPlayingMoviesError(): LiveData<String> {
        return nowPlayingMoviesError
    }

    /**
     * Function to return most popular movies response from server
     */
    fun mostPopularMoviesResponse(): LiveData<PopularMovie> {
        return mostPopularMoviesResponse
    }

    /**
     * Function to return most popular movies error from server
     */
    fun mostPopularMoviesError(): LiveData<String> {
        return mostPopularMoviesError
    }

    /**
     * Function to load now playing movies
     */
    fun loadNowPlayingMovies() {
        disposableObserverNowPlayingMovie = object : DisposableObserver<NowPlayingMovie>() {
            override fun onComplete() {
            }

            override fun onNext(nowPlayingMovie: NowPlayingMovie) {
                nowPlayingMoviesResponse.postValue(nowPlayingMovie)
            }

            override fun onError(e: Throwable) {
                nowPlayingMoviesError.postValue(e.message)
            }
        }

        movieRepository.getNowPlayingMovies()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserverNowPlayingMovie)
    }

    /**
     * Function to load most popular movies
     */
    fun loadMostPopularMovies(page: Int) {
        disposableObserverMostPopular = object : DisposableObserver<PopularMovie>() {
            override fun onComplete() {
            }

            override fun onNext(popularMovie: PopularMovie) {
                mostPopularMoviesResponse.postValue(popularMovie)
            }

            override fun onError(e: Throwable) {
                mostPopularMoviesError.postValue(e.message)
            }
        }

        movieRepository.getMostPopularMovies(page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserverMostPopular)
    }

    /**
     * Function to dispose observers
     */
    fun disposeElements() {
        if (null != disposableObserverNowPlayingMovie && !disposableObserverNowPlayingMovie.isDisposed) disposableObserverNowPlayingMovie.dispose()
        if (null != disposableObserverMostPopular && !disposableObserverMostPopular.isDisposed) disposableObserverMostPopular.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposeElements()
    }
}