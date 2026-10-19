package com.example.tp05_coil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tp05_coil.adapter.MovieAdapter
import com.example.tp05_coil.databinding.ActivityMainBinding
import com.example.tp05_coil.model.Movie
import com.example.tp05_coil.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    private val editorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data!!
            val action = data.getStringExtra(MovieEditorActivity.EXTRA_ACTION)
            val id = data.getStringExtra(MovieEditorActivity.EXTRA_ID)
            val title = data.getStringExtra(MovieEditorActivity.EXTRA_TITLE).orEmpty()
            val rating = data.getFloatExtra(MovieEditorActivity.EXTRA_RATING, 0f)
            val imageUrl = data.getStringExtra(MovieEditorActivity.EXTRA_IMAGE_URL).orEmpty()

            when (action) {
                MovieEditorActivity.ACTION_SAVE -> {
                    if (id != null) {
                        viewModel.updateMovie(id, title, rating, imageUrl)
                        Toast.makeText(this, "Filme atualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addMovie(title, rating, imageUrl)
                        Toast.makeText(this, "Filme adicionado", Toast.LENGTH_SHORT).show()
                    }
                }
                MovieEditorActivity.ACTION_DELETE -> {
                    if (id != null) {
                        viewModel.deleteMovie(id)
                        Toast.makeText(this, "Filme removido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()

        binding.fabAddMovie.setOnClickListener {
            openEditor(null)
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            openEditor(movie)
        }
        binding.recyclerViewMovies.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
        }
    }

    private fun setupObservers() {
        viewModel.movies.observe(this) { movies ->
            movieAdapter.updateList(movies)
        }
    }

    private fun openEditor(movie: Movie?) {
        val intent = Intent(this, MovieEditorActivity::class.java).apply {
            if (movie != null) {
                putExtra(MovieEditorActivity.EXTRA_ID, movie.id)
                putExtra(MovieEditorActivity.EXTRA_TITLE, movie.title)
                putExtra(MovieEditorActivity.EXTRA_RATING, movie.rating)
                putExtra(MovieEditorActivity.EXTRA_IMAGE_URL, movie.imageUrl)
            }
        }
        editorLauncher.launch(intent)
    }
}
