package com.antoniomy82.moviesdb_challenge.model

import com.antoniomy82.moviesdb_challenge.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("movie/popular?api_key=${Constant.apiKey}&language=en-US&page=1")
    fun getPopularMovies(): Call<MoviesList>

    @GET("movie/top_rated?&api_key=${Constant.apiKey}")
    fun getTopRatedMovies(): Call<Movie>

    @GET("movie/upcoming?&api_key=${Constant.apiKey}")
    fun getUpComingMovies(): Call<Movie>

    @GET("movie/{id}?&api_key=${Constant.apiKey}")
    fun getMovieDetails(@Path("id") movieId: Int): Call<Movie>

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id") movieId: Int): Call<Movie>

    @GET("search/movie?&api_key=${Constant.apiKey}")
    fun getSearchMovies(@Query("query") search: String): Call<Movie>

}