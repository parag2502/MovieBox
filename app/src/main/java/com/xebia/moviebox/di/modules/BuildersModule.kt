package com.xebia.moviebox.di.modules

import com.xebia.moviebox.views.MovieActivity
import com.xebia.moviebox.views.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieActivity(): MovieActivity
    @ContributesAndroidInjector
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity
}