package com.example.tp05_coil

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tp05_coil.databinding.ActivityMovieEditorBinding

class MovieEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieEditorBinding
    private var movieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getStringExtra(EXTRA_ID)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val rating = intent.getFloatExtra(EXTRA_RATING, -1f)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)

        if (movieId != null) {
            binding.textViewEditorTitle.text = getString(R.string.edit_movie)
            binding.editTextTitle.setText(title)
            if (rating >= 0) {
                binding.editTextRating.setText(rating.toString())
            }
            binding.editTextImageUrl.setText(imageUrl)
            binding.buttonDelete.visibility = View.VISIBLE
        } else {
            binding.textViewEditorTitle.text = getString(R.string.add_movie)
            binding.buttonDelete.visibility = View.GONE
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonDelete.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra(EXTRA_ACTION, ACTION_DELETE)
                putExtra(EXTRA_ID, movieId)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.buttonSave.setOnClickListener {
            val inputTitle = binding.editTextTitle.text.toString().trim()
            val inputRatingString = binding.editTextRating.text.toString().trim()
            val inputImageUrl = binding.editTextImageUrl.text.toString().trim()

            if (inputTitle.isEmpty()) {
                binding.editTextTitle.error = "Preencha o nome"
                return@setOnClickListener
            }

            val inputRating = inputRatingString.toFloatOrNull() ?: 0.0f

            val resultIntent = Intent().apply {
                putExtra(EXTRA_ACTION, ACTION_SAVE)
                putExtra(EXTRA_ID, movieId)
                putExtra(EXTRA_TITLE, inputTitle)
                putExtra(EXTRA_RATING, inputRating)
                putExtra(EXTRA_IMAGE_URL, inputImageUrl)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_ACTION = "EXTRA_ACTION"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_RATING = "EXTRA_RATING"
        const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"

        const val ACTION_SAVE = "ACTION_SAVE"
        const val ACTION_DELETE = "ACTION_DELETE"
    }
}
