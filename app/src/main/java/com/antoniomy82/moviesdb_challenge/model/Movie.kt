package com.antoniomy82.moviesdb_challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "MOVIE_ID") var movieId: Int,
    @ColumnInfo(name = "POSTER_PATH") var poster_path: String? = null,
    @ColumnInfo(name = "OVERVIEW") var overview: String? = null,
    @ColumnInfo(name = "RELEASE_DATE") var release_date: String? = null,
    @ColumnInfo(name = "ORIGINAL_TITLE") var original_title: String? = null,
    @ColumnInfo(name = "ORIGINAL_LANGUAGE") var original_language: String? = null,
    @ColumnInfo(name = "TITLE") var title: String? = null,
    @ColumnInfo(name = "BACKDROP_PATH") var backdrop_path: String? = null,
    @ColumnInfo(name = "POPULARITY") var popularity: Double? = 0.0,
    @ColumnInfo(name = "VOTE_COUNT") var vote_count: Int? = 0,
    @ColumnInfo(name = "VIDEO") var video: Boolean? = false,
    @ColumnInfo(name = "VOTE_AVERAGE") var vote_average: Double? = 0.0
)

