package com.xebia.moviebox.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xebia.moviebox.viewmodels.DetailMovieViewModel
import com.xebia.moviebox.viewmodels.MovieViewModel
import javax.inject.Inject

class MovieViewModelFactory @Inject constructor(
    private val movieViewModel: MovieViewModel,
    private val detailMovieViewModel: DetailMovieViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java!!)) {
            return movieViewModel as T
        }
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java!!)) {
            return detailMovieViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}