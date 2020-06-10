# MovieBox

# Overview:

The purpose of this application is to create a simple App to display the movies using TheMovieDB API.

MovieBox application is developed using Kotlin and followed by MVVM architecture and Observer Design Pattern is also used.

# Third party libraries:

Dagger2: It is a compile-time framework for dependency injection.

Lifecycle: Build lifecycle-aware components that can adjust behavior based on the current lifecycle state of an activity.

LiveData: LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware.

RxJava, RxAndroid: It provides a set of tools to help you write clean and simpler code.

Retrofit: Retrofit is a REST Client for Java and Android. It handles the REST API request and response.

Glide: Glide is a fast and efficient way to handle the remote images.

FlowLayout: FlowLayout is used to arrange components in a sequence one after the other.

# Source code:

In the source code following packages are used for,

adapters: To handle adapters of recycler views

custom: To design custom views like endless recyclerview.

di: To handle dependency injection related tasks.

models: To handle the models.

views: To handle the views like MovieActivity, MovieDetailActivity.

viewmodels: To handle the View models, basically ViewModel is a class that is responsible for preparing and managing the data for views.

repositories: It handles remote data repositories.

utils: Its used to do task related to Application Constants and utilities.

# Test:

I have tested the app its working fine.
I have written a Expresso Unit test case

# Note:

1. To handle the image caching I have used Glide library and in that used DiskCacheStrategy.ALL
2. For Custom rating view, I have developed custom design for progress bar using RelativeLayout, ProgressBar and TextView
3. Application is developed in portrait mode only
4. Minimum Supported versions is 5.0+
5. Movie Duration is not coming in the PopularMoviesAPI

Thanks
