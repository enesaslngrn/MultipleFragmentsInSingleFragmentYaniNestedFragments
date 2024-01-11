package com.enesas.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enesas.movieapp.model.popularmovies.ResultPopularMovies


/*
    Buraya @PrimaryKey'deki uuid'ler gelecek. her bir Results() listesi içindeki 20 adet filmin uuidsi. O yüzden Long
    Böyle olması seni şaşırtmasın.
    Yani neden sadece @PrimaryKey ile verdiğimiz uuid dönüyor ama Result() sınıfındaki diğer
    tüm diğer veriler niye gelmiyor diye düşünme.
    Bu Room kütüphanesinin bir özelliği. @Insert diyerek her oluşturduğumuz Result
    listesindeki veriler için uuid'leri döndürüyoruz.

     */

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrending(id : ResultPopularMovies) : Long

    @Query("SELECT * FROM resultpopularmovies")
    fun getAllTrending() : LiveData<ResultPopularMovies>

    @Query("SELECT * FROM resultpopularmovies WHERE id = :movieId")
    suspend fun getTrendingMovie(movieId : Int) : ResultPopularMovies

    @Query("DELETE FROM resultpopularmovies")
    suspend fun deleteAllTrending()


}