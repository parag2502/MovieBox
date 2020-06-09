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

class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    var nowPlayingMoviesResponse: MutableLiveData<NowPlayingMovie> = MutableLiveData()
    var nowPlayingMoviesError: MutableLiveData<String> = MutableLiveData()
    var mostPopularMoviesResponse: MutableLiveData<PopularMovie> = MutableLiveData()
    var mostPopularMoviesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserverNowPlayingMovie: DisposableObserver<NowPlayingMovie>
    lateinit var disposableObserverMostPopular: DisposableObserver<PopularMovie>

    fun nowPlayingMoviesResponse(): LiveData<NowPlayingMovie> {
        return nowPlayingMoviesResponse
    }

    fun nowPlayingMoviesError(): LiveData<String> {
        return nowPlayingMoviesError
    }

    fun mostPopularMoviesResponse(): LiveData<PopularMovie> {
        return mostPopularMoviesResponse
    }

    fun mostPopularMoviesError(): LiveData<String> {
        return mostPopularMoviesError
    }

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