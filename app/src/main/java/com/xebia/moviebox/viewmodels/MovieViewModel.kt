package com.xebia.moviebox.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xebia.moviebox.models.NowPlayingMovie
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
    lateinit var disposableObserverNowPlayingMovie: DisposableObserver<NowPlayingMovie>

    fun nowPlayingMoviesResponse(): LiveData<NowPlayingMovie> {
        return nowPlayingMoviesResponse
    }

    fun nowPlayingMoviesError(): LiveData<String> {
        return nowPlayingMoviesError
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

    /**
     * Function to dispose observers
     */
    fun disposeElements() {
        if (null != disposableObserverNowPlayingMovie && !disposableObserverNowPlayingMovie.isDisposed) disposableObserverNowPlayingMovie.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposeElements()
    }
}