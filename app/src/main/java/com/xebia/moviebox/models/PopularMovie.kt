package com.xebia.moviebox.models


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("vote_average")
        val voteAverage: String,
        @SerializedName("release_date")
        val releaseDate: String
    )
}