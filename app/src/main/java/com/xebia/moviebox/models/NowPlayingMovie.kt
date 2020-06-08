package com.xebia.moviebox.models


import com.google.gson.annotations.SerializedName

data class NowPlayingMovie(
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("id")
        val id: Int
    )
}