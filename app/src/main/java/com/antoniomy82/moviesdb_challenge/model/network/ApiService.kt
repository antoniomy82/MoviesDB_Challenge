package com.antoniomy82.moviesdb_challenge.model.network

import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
/*
    var lang: String
        get() = "en-US"
        set(value) = TODO()
*/
    @GET
    fun getPopularMovies(@Url mUrl: String): Call<MoviesList>

    @GET("movie/top_rated?&api_key=${Constant.apiKey}")
    fun getTopRatedMovies(): Call<Movie>

    @GET("movie/upcoming?&api_key=${Constant.apiKey}")
    fun getUpComingMovies(): Call<Movie>

    @GET("movie/{id}?&api_key=${Constant.apiKey}")
    fun getMovieDetails(@Path("id") movieId: Int): Call<Movie>

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id") movieId: Int): Call<Movie>

    @GET("search/movie?&api_key=${Constant.apiKey}")
    fun getSearchMovies(@Query("query") search: String): Call<MoviesList>

}