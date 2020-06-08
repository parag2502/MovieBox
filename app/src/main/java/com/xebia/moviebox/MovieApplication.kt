package com.xebia.moviebox

import android.app.Activity
import android.app.Application
import com.xebia.moviebox.di.component.DaggerAppComponent
import com.xebia.moviebox.di.modules.AppModule
import com.xebia.moviebox.di.modules.MovieModule
import com.xebia.moviebox.utils.AppConstants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MovieApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .movieModule(MovieModule(AppConstants.MOVIE_BASE_URL))
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}