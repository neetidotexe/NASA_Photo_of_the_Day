package com.example.nasaphotooftheday.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "npod")
data class NasaPhoto(

    @PrimaryKey
    @SerializedName("date")
    val date : Date,

    @SerializedName("explanation")
    val description : String?,

    @SerializedName("hdurl")
    val hdurl : String?,

    @SerializedName("media_type")
    val media_type : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("url")
    val url : String?,

    @SerializedName("thumbnail_url")
    val thumbnail_url : String?
)