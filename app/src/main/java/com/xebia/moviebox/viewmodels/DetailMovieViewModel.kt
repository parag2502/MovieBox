package com.xebia.moviebox.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xebia.moviebox.models.MovieDetail
import com.xebia.moviebox.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    var detailMovieResponse: MutableLiveData<MovieDetail> = MutableLiveData()
    var detailMoviesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserverDetailMovie: DisposableObserver<MovieDetail>

    fun detailMovieResponse(): LiveData<MovieDetail> {
        return detailMovieResponse
    }

    fun detailMoviesError(): LiveData<String> {
        return detailMoviesError
    }

    fun loadMovieDetail(movieId: Int) {
        disposableObserverDetailMovie = object : DisposableObserver<MovieDetail>() {
            override fun onComplete() {
            }

            override fun onNext(movieDetail: MovieDetail) {
                detailMovieResponse.postValue(movieDetail)
            }

            override fun onError(e: Throwable) {
                detailMoviesError.postValue(e.message)
            }
        }

        movieRepository.getMovieDetail(movieId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserverDetailMovie)
    }

    /**
     * Function to dispose observers
     */
    fun disposeElements() {
        if (null != disposableObserverDetailMovie && !disposableObserverDetailMovie.isDisposed) disposableObserverDetailMovie.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposeElements()
    }
}