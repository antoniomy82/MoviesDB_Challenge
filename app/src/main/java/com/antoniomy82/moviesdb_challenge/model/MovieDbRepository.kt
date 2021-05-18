package com.antoniomy82.moviesdb_challenge.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieDbRepository {

    private val apiAdapter=ApiAdapter().api
    val retrieveMovie = MutableLiveData<Movie>()
    val retrieveMovies = MutableLiveData<MoviesList>()

    fun getTopRatedMovies(context:Context): MutableLiveData<Movie> {

        return mCallback(apiAdapter?.getTopRatedMovies(),context)
    }

    fun getPopularMovies(context:Context): MutableLiveData<MoviesList> {

        return listCallback(apiAdapter?.getPopularMovies(),context)
    }

    fun getUpcomingMovies(): Call<Movie>?{
        return apiAdapter?.getUpComingMovies()
    }

    fun getMovieDetails(context:Context, movieId:Int): LiveData<Movie>{
        return mCallback(apiAdapter?.getMovieDetails(movieId), context)
    }

    fun getMovieTrailers(movieId: Int): Call<Movie>?{
        return apiAdapter?.getMovieVideos(movieId)
    }

    fun getSearchMovies(search:String):Call<Movie>?{
        return apiAdapter?.getSearchMovies(search)
    }


    private fun mCallback(call: Call<Movie>?, context: Context): MutableLiveData<Movie>{

        call?.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

                var mResponse: Movie ?=null
                val defaultLanguage: Locale = Locale.getDefault()

                Log.d("Response", defaultLanguage.toString())
                if (response.isSuccessful) {
                    Log.d("Response", "ok")
                    val responseObtained = response.body()


                    mResponse = responseObtained?.movieId?.let {
                        Movie(
                            it,
                            responseObtained.poster_path,
                            responseObtained.overview,
                            responseObtained.release_date,
                            responseObtained.original_title,
                            responseObtained.original_language,
                            responseObtained.title,
                            responseObtained.backdrop_path,
                            responseObtained.popularity,
                            responseObtained.vote_count,
                            responseObtained.video,
                            responseObtained.vote_average
                        )
                    }
                     Log.d("__retrieveData",responseObtained.toString())
                }else   Log.d("__restrie","No response")

                retrieveMovie.value=mResponse

            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("__error", t.toString())
                Toast.makeText(context, "ERROR API SERVICES", Toast.LENGTH_LONG).show()
                // PoisUtils.replaceFragment(HomeDistrictFragment(),  (context as AppCompatActivity).supportFragmentManager)
            }

        })


        return retrieveMovie
    }


    private fun listCallback(call: Call<MoviesList>?, context: Context): MutableLiveData<MoviesList>{



        call?.enqueue(object : Callback<MoviesList> {
            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {

                if (response.isSuccessful) {

                    val responseObtained = response.body()

                    val results = mutableListOf<Movie>()

                    val resultSize = responseObtained?.results?.size ?: 0
                    for (i in 0 until resultSize) {
                        results.add(
                            Movie(
                                responseObtained?.results?.get(i)?.movieId ?: 0,
                                responseObtained?.results?.get(i)?.poster_path,
                                responseObtained?.results?.get(i)?.overview,
                                responseObtained?.results?.get(i)?.release_date,
                                responseObtained?.results?.get(i)?.original_title,
                                responseObtained?.results?.get(i)?.original_language,
                                responseObtained?.results?.get(i)?.title,
                                responseObtained?.results?.get(i)?.backdrop_path,
                                responseObtained?.results?.get(i)?.popularity ?: 0.0,
                                responseObtained?.results?.get(i)?.vote_count ?: 0,
                                responseObtained?.results?.get(i)?.video ?: false,
                                responseObtained?.results?.get(i)?.vote_average ?: 0.0
                            )
                        )

                        Log.d(
                            "__retrieveList ",
                            i.toString()+" - " + responseObtained?.results?.get(i)?.title
                        )
                    }

                    retrieveMovies.value = MoviesList(
                        page = responseObtained?.page,
                        results = results,
                        totalPages = responseObtained?.totalPages,
                        totalResult = responseObtained?.totalResult
                    )
                }
            }

            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                Log.e("__error", t.toString())
                Toast.makeText(context, "ERROR API SERVICES", Toast.LENGTH_LONG).show()
            }
        })


        return retrieveMovies
    }
}
