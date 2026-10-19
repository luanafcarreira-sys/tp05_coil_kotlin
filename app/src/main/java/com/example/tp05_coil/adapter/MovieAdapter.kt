package com.example.tp05_coil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp05_coil.databinding.ItemMovieBinding
import com.example.tp05_coil.model.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun updateList(newList: List<Movie>) {
        movieList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.textViewTitle.text = movie.title
            binding.textViewRating.text = "★ %.1f / 5".format(movie.rating)

            binding.imageViewPoster.load(movie.imageUrl) {
                crossfade(true)
                error(android.R.drawable.ic_menu_report_image)
            }

            binding.cardMovie.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }
}
