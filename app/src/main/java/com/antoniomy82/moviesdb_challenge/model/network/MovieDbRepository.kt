package com.antoniomy82.moviesdb_challenge.model.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.antoniomy82.moviesdb_challenge.R
import com.antoniomy82.moviesdb_challenge.model.Movie
import com.antoniomy82.moviesdb_challenge.utils.CommonUtil
import com.antoniomy82.moviesdb_challenge.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDbRepository {


    fun getPopularMovies(
        context: Context? = null,
        mMoviesList: MutableLiveData<MoviesList>? = null,
        mUrl: String? = null
    ): Int {

        var mockTest = 0

        if (mUrl != null && context != null && mMoviesList != null) listCallback(
            ApiAdapter().api?.getPopularMovies(
                mUrl
            ), context, mMoviesList
        )
        else mockTest = 500

        return mockTest
    }


    fun getSearchMovies(
        search: String? = null,
        context: Context? = null,
        mMoviesList: MutableLiveData<MoviesList>? = null
    ): Boolean {
        var mockTest = false

        if (search != null && context != null && mMoviesList != null) {
            val languageSearch =
                "https://api.tmdb.org/3/search/movie?api_key=${Constant.apiKey}&query=" + search + "&language=" + CommonUtil.getLanguage() + "&page=" + CommonUtil.actualPage
            listCallback(ApiAdapter().api?.getSearchMovies(languageSearch), context, mMoviesList)
        } else mockTest = true

        return mockTest
    }


    private fun listCallback(
        call: Call<MoviesList>?,
        context: Context,
        mMoviesList: MutableLiveData<MoviesList>
    ) {

        val results = mutableListOf<Movie>()


        call?.enqueue(object : Callback<MoviesList> {
            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {

                if (response.isSuccessful) {

                    val responseObtained = response.body()


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
                            i.toString() + " - " + responseObtained?.results?.get(i)?.title + " page " + responseObtained?.page + " totalPage " + responseObtained?.total_pages
                        )
                    }


                    mMoviesList.value = MoviesList(
                        page = responseObtained?.page.toString(),
                        results = results,
                        total_pages = responseObtained?.total_pages,
                        total_result = responseObtained?.total_result
                    )


                    if (resultSize == 0) {
                        results.clear()
                        results.add(
                            Movie(
                                "",
                                context.getString(R.string.try_another_movie),
                                "",
                                "",
                                "",
                                context.getString(R.string.no_search_found)
                            )
                        )
                        mMoviesList.value = MoviesList("0", results, "0")
                    }

                }

            }

            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                Log.e("__error", t.toString())
                Toast.makeText(
                    context,
                    context.getString(R.string.error_api_services),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}
