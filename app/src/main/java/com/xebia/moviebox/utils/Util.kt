package com.xebia.moviebox.utils

object Util {
    /**
     * Function to calculate movie duration
     */
    fun getMovieDuration(time: Int): String {
        var strDuration: String
        val hours: Int = time / 60
        val minutes: Int = time % 60
        strDuration = "${hours}h ${minutes}m"
        return strDuration
    }
}