package com.antoniomy82.moviesdb_challenge.model

data class MoviesList(
    var page:Int?=0,
    var results: List<Movie> ?=null,
    var totalPages:Int?=0,
    var totalResult:Int?=0
)
