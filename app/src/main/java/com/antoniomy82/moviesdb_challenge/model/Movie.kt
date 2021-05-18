package com.antoniomy82.moviesdb_challenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(

    @PrimaryKey
    var movieId: Int,
    var poster_path: String? = null,
    var overview: String? = null,
    var release_date: String? = null,
    var original_title: String? = null,
    var original_language: String? = null,
    var title: String? = null,
    var backdrop_path: String? = null,
    var popularity: Double,
    var vote_count: Int,
    var video: Boolean,
    var vote_average: Double
)

