package com.xebia.moviebox.di.component

import com.xebia.moviebox.MovieApplication
import com.xebia.moviebox.di.modules.AppModule
import com.xebia.moviebox.di.modules.BuildersModule
import com.xebia.moviebox.di.modules.MovieModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class, BuildersModule::class, AppModule::class,
        MovieModule::class
    )
)
interface AppComponent {
    fun inject(app: MovieApplication)
}