package com.antoniomy82.moviesdb_challenge.model.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.antoniomy82.moviesdb_challenge.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDbRepository {

    //DB instance
    private var moviesLocalDB: MoviesLocalDB? = null

    private fun initializeDB(context: Context): MoviesLocalDB? {
        return MoviesLocalDB.getDatabaseClient(context)
    }


    fun insertMovie(context: Context, movie: Movie) {

        moviesLocalDB = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            moviesLocalDB?.moviesLocalDAO()?.insertMovie(movie)
        }

    }

    fun deleteMovie(context: Context, title: String) {
        moviesLocalDB = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                moviesLocalDB?.moviesLocalDAO()?.deleteMovie(title)
            } catch (e: Exception) {
                Log.e("__deleteError", e.toString())
            }

        }

    }

    fun getAllMovies(context: Context, moviesList: MutableLiveData<List<Movie>>) {
        moviesLocalDB = initializeDB(context)

        val mList = mutableListOf<Movie>()

        CoroutineScope(Dispatchers.IO).launch {

            val mSize = moviesLocalDB?.moviesLocalDAO()?.getAllMovies()?.size ?: 0

            for (i in 0 until mSize) {
                moviesLocalDB?.moviesLocalDAO()?.getAllMovies()?.get(i)?.let {
                    mList.add(
                        i,
                        it
                    )
                }
            }
            moviesList.postValue(mList)
        }
    }

    fun getSavedMovies(context: Context, moviesList: MutableLiveData<List<String>>) {
        moviesLocalDB = initializeDB(context)

        val mList = mutableListOf<String>()

        CoroutineScope(Dispatchers.IO).launch {

            val mSize = moviesLocalDB?.moviesLocalDAO()?.getAllMovies()?.size ?: 0

            for (i in 0 until mSize) {
                moviesLocalDB?.moviesLocalDAO()?.getAllMovies()?.get(i)?.let {
                    mList.add(
                        i,
                        it.title
                    )
                }
            }
            moviesList.postValue(mList)
        }
    }
}