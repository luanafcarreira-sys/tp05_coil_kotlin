package com.example.tp05_coil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp05_coil.model.Movie

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>> = _movies

    fun addMovie(title: String, rating: Float, imageUrl: String) {
        val currentList = _movies.value.orEmpty().toMutableList()
        currentList.add(0, Movie(title = title, rating = rating, imageUrl = imageUrl))
        _movies.value = currentList
    }

    fun updateMovie(id: String, title: String, rating: Float, imageUrl: String) {
        val currentList = _movies.value.orEmpty().toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = Movie(id = id, title = title, rating = rating, imageUrl = imageUrl)
            _movies.value = currentList
        }
    }

    fun deleteMovie(id: String) {
        val currentList = _movies.value.orEmpty().toMutableList()
        currentList.removeAll { it.id == id }
        _movies.value = currentList
    }
}
