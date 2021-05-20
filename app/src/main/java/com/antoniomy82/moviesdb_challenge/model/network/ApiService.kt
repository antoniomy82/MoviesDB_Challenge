package com.antoniomy82.moviesdb_challenge.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {


    @GET
    fun getPopularMovies(@Url mUrl: String): Call<MoviesList>

    @GET
    fun getSearchMovies(@Url mUrl: String): Call<MoviesList>

}