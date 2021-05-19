package com.antoniomy82.moviesdb_challenge.model.network

import com.antoniomy82.moviesdb_challenge.model.Movie

data class MoviesList(
    var page: String? = null,
    var results: List<Movie>? = null,
    var total_pages: String? = null,
    var total_result: String? = null
)
