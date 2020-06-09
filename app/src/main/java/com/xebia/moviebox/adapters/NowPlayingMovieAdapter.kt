package com.xebia.moviebox.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xebia.moviebox.R
import com.xebia.moviebox.models.NowPlayingMovie
import com.xebia.moviebox.utils.AppConstants
import com.xebia.moviebox.views.MovieDetailActivity

class NowPlayingMovieAdapter(var _context: Context) :
    RecyclerView.Adapter<NowPlayingMovieAdapter.ViewHolder>() {
    private var nowPlayingMovieList: ArrayList<NowPlayingMovie.Result> = ArrayList()
    public var context: Context = _context

    /**
     * Function is returning the view for each item in the list
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_playing_now_item, parent, false)
        return ViewHolder(v)
    }

    /**
     * Function is binding the data on the list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(nowPlayingMovieList[position])
    }

    /**
     * Function is giving the size of the list
     */
    override fun getItemCount(): Int {
        return nowPlayingMovieList.size
    }

    /**
     * Function to set the movie data
     */
    fun setMovieData(_nowPlayingMovieList: ArrayList<NowPlayingMovie.Result>) {
        nowPlayingMovieList = _nowPlayingMovieList
        notifyDataSetChanged()
    }

    /**
     * The class is holding the list view
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(nowPlayingMovie: NowPlayingMovie.Result) {
            // Handle Movie Poster ImageView
            val ivMoviePoster = itemView.findViewById(R.id.iv_movie) as ImageView
            val posterUrl = AppConstants.MOVIE_IMAGE_URL + nowPlayingMovie.posterPath
            Glide.with(itemView).load(posterUrl)
                .placeholder(R.color.colorLightGray).fitCenter()
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                ).into(ivMoviePoster)
            // Handle click event of item
            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(AppConstants.KEY_MOVIE_ID, nowPlayingMovie.id)
                context.startActivity(intent)
            }
        }
    }
}