package com.antoniomy82.moviesdb_challenge.utils

object Constant {
    const val baseUrl = "https://api.themoviedb.org/3/"

    const val apiKey = "d9604db7fa05026f78ec65ba008e0b24"
    val dynamicUrl= "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey&language="+CommonUtil.getLanguage()+"&page="
    const val basePoster = "https://image.tmdb.org/t/p/w500"
}