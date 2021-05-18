package com.antoniomy82.moviesdb_challenge.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antoniomy82.moviesdb_challenge.model.Movie

@Dao
interface MoviesLocalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("DELETE FROM Movie WHERE title =:title")
    fun deleteMovie(title: String)

    @Query("SELECT * FROM Movie")
    fun getAllMovies(): List<Movie>

}