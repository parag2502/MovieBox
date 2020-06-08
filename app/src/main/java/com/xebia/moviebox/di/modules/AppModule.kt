package com.xebia.moviebox.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.xebia.moviebox.viewmodels.factory.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    fun provideMovieViewModelFactory(
        factory: MovieViewModelFactory
    ): ViewModelProvider.Factory = factory
}