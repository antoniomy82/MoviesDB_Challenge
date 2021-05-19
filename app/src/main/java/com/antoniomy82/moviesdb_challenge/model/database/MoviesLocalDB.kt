package com.antoniomy82.moviesdb_challenge.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.antoniomy82.moviesdb_challenge.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)

abstract  class MoviesLocalDB: RoomDatabase() {

    abstract fun moviesLocalDAO() : MoviesLocalDAO

    companion object{

        private var MoviesINSTANCE : MoviesLocalDB?=null

        fun getDatabaseClient(context: Context): MoviesLocalDB? {


            if(MoviesINSTANCE !=null) return MoviesINSTANCE

            synchronized(MoviesLocalDB::class.java){
                MoviesINSTANCE =
                    Room.databaseBuilder(context, MoviesLocalDB::class.java, "MoviesLocalDB").fallbackToDestructiveMigration().build()
                return MoviesINSTANCE
            }
        }
    }
}