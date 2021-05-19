package com.antoniomy82.moviesdb_challenge.model.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDbRepository {

    private val apiAdapter = ApiAdapter().api
    private val mUrl="https://api.themoviedb.org/3/movie/popular?api_key="+Constant.apiKey+"&language=en-US&page=1"

    fun getPopularMovies(context: Context, mMoviesList: MutableLiveData<MoviesList>, mUrl:String) {
        listCallback(apiAdapter?.getPopularMovies(mUrl), context, mMoviesList)
    }



    fun getSearchMovies(
        search: String,
        context: Context,
        mMoviesList: MutableLiveData<MoviesList>
    ) {
        listCallback(apiAdapter?.getSearchMovies(search), context, mMoviesList)
    }


    private fun listCallback(
        call: Call<MoviesList>?,
        context: Context,
        mMoviesList: MutableLiveData<MoviesList>
    ) {

        call?.enqueue(object : Callback<MoviesList> {
            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {

                if (response.isSuccessful) {

                    val responseObtained = response.body()

                    val results = mutableListOf<Movie>()

                    val resultSize = responseObtained?.results?.size ?: 0
                    for (i in 0 until resultSize) {
                        responseObtained?.results?.get(i)?.title?.let {
                            Movie(
                                responseObtained.results?.get(i)?.poster_path,
                                responseObtained.results?.get(i)?.overview,
                                responseObtained.results?.get(i)?.release_date,
                                responseObtained.results?.get(i)?.original_title,
                                responseObtained.results?.get(i)?.original_language,
                                it,
                                responseObtained.results?.get(i)?.backdrop_path,
                                responseObtained.results?.get(i)?.popularity ?: 0.0,
                                responseObtained.results?.get(i)?.vote_count ?: 0,
                                responseObtained.results?.get(i)?.video ?: false,
                                responseObtained.results?.get(i)?.vote_average ?: 0.0
                            )
                        }?.let {
                            results.add(
                                it
                            )
                        }

                        Log.d(
                            "__retrieveList ",
                            i.toString() + " - " + responseObtained?.results?.get(i)?.title + " page "+responseObtained?.page + " totalPage " + responseObtained?.total_pages
                        )
                    }

                    mMoviesList.value = MoviesList(
                        page = responseObtained?.page.toString(),
                        results = results,
                        total_pages = responseObtained?.total_pages,
                        total_result = responseObtained?.total_result
                    )
                }
            }

            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                Log.e("__error", t.toString())
                Toast.makeText(context, "ERROR API SERVICES", Toast.LENGTH_LONG).show()
            }
        })
    }
}
