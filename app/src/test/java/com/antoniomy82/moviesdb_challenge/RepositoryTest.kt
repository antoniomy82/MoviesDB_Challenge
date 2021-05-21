package com.antoniomy82.moviesdb_challenge

import com.antoniomy82.moviesdb_challenge.model.database.LocalDbRepository
import com.antoniomy82.moviesdb_challenge.model.network.MovieDbRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepositoryTest {

    private var networkMockRepository: MovieDbRepository? = null
    private var localMockRepository : LocalDbRepository ?=null


    @BeforeEach
    fun setUp() {
        networkMockRepository = MovieDbRepository()
        localMockRepository = LocalDbRepository()
        println("@BeforeEach -> setUp()")
    }


    @Test
    fun getPopularMoviesTest() {
        assertEquals(500, networkMockRepository?.getPopularMovies())
        println("@Test -> getPopularMoviesTest()")
    }

    @Test
    fun getSearchMoviesTest() {
        assertEquals(true, networkMockRepository?.getSearchMovies())
        println("@Test -> getSearchMoviesTest()")
    }

    @Test
    fun getSavedMoviesTest() {
        assertEquals(true, localMockRepository?.getSavedMovies())
        println("@Test -> getSavedMoviesTest()")
    }

    @Test
    fun getAllMoviesTest() {
        assertNotNull(localMockRepository?.getAllMovies(), "Error")
        println("@Test -> getAllMoviesTest()")
    }


    @Test
    fun insertMovieTest() {
        assertEquals(true, localMockRepository?.insertMovie())
        println("@Test -> insertMovieTest()")
    }

    @Test
    fun deleteMovieTest() {
        assertEquals(true, localMockRepository?.insertMovie())
        println("@Test -> deleteMovieTest()")
    }


}