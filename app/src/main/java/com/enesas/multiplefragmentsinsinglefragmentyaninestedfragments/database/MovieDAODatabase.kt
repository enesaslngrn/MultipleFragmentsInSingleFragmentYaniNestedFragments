package com.enesas.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesas.movieapp.model.popularmovies.ResultPopularMovies


@Database(entities = arrayOf(
    ResultPopularMovies::class),
    version = 1)
abstract class MovieDAODatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDAO


    companion object{
        @Volatile
        private var INSTANCE : MovieDAODatabase? = null

        private val lock  = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock){ // INSTANCE null değilse direkt bunu döndür. Eğer henüz bir INSTANCE oluşturulmadıysa synchronized döndür
            INSTANCE ?: createDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDAODatabase::class.java, "moviedatabase").build()
    }

}