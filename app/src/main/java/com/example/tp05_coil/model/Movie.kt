package com.example.tp05_coil.model

import java.util.UUID

data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val rating: Float,
    val imageUrl: String
)
