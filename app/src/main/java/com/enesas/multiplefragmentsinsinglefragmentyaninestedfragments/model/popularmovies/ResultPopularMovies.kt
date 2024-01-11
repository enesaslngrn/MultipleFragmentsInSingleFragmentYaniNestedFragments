package com.enesas.movieapp.model.popularmovies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("resultpopularmovies")
data class ResultPopularMovies(
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @ColumnInfo("overview")
    val overview: String?,
    @ColumnInfo("popularity")
    val popularity: Double?,
    @ColumnInfo("poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,
    @ColumnInfo("release_date")
    @SerializedName("release_date")
    val releaseDate: String?,
    @ColumnInfo("title")
    val title: String?,
    val video: Boolean?,
    @ColumnInfo("vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @ColumnInfo("vote_count")
    @SerializedName("vote_count")
    val voteCount: Int?
)
