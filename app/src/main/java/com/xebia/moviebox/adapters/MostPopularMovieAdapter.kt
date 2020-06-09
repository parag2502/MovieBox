package com.xebia.moviebox.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xebia.moviebox.R
import com.xebia.moviebox.models.PopularMovie
import com.xebia.moviebox.utils.AppConstants
import com.xebia.moviebox.views.MovieDetailActivity

class MostPopularMovieAdapter(var _context: Context) :
    RecyclerView.Adapter<MostPopularMovieAdapter.ViewHolder>() {
    private var mostPopularMovieList: ArrayList<PopularMovie.Result> = ArrayList()
    public var context: Context = _context

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_most_popular_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mostPopularMovieList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return mostPopularMovieList.size
    }

    fun setMovieData(_nowPlayingMovieList: ArrayList<PopularMovie.Result>) {
        mostPopularMovieList = _nowPlayingMovieList
        notifyDataSetChanged()
    }

    /**
     * The class is holding the list view
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(popularMovie: PopularMovie.Result) {
            val ivMoviePoster = itemView.findViewById(R.id.iv_poster) as ImageView
            val posterUrl = AppConstants.MOVIE_IMAGE_URL + popularMovie.posterPath
            Glide.with(itemView).load(posterUrl)
                .dontAnimate().fitCenter().into(ivMoviePoster)
            val tvTitle = itemView.findViewById(R.id.tv_title) as TextView
            tvTitle.text = popularMovie.title
            val tvReleaseDate = itemView.findViewById(R.id.tv_release_date) as TextView
            tvReleaseDate.text = popularMovie.releaseDate
            val pbRating = itemView.findViewById(R.id.pb_rating) as ProgressBar
            val rating = (popularMovie.voteAverage.toFloat() * 10).toInt()
            if(rating >= 50){
                pbRating.progressDrawable = context.getDrawable(R.drawable.progress_bar_circular_green)
            } else {
                pbRating.progressDrawable = context.getDrawable(R.drawable.progress_bar_circular_yellow)
            }
            pbRating.progress = rating

            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(AppConstants.KEY_MOVIE_ID, popularMovie.id)
                context.startActivity(intent)
            }
        }
    }
}