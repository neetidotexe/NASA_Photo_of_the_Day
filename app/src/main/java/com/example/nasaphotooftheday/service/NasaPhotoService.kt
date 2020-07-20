package com.example.nasaphotooftheday.service

import com.example.nasaphotooftheday.database.entity.NasaPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY
//https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=YYYY-MM-DD

interface NasaPhotoService {

    @GET("/planetary/apod")
    fun getNasaPhoto() : Single<NasaPhoto>

    @GET("/planetary/apod")
    fun getNasaPhotoOnDate(@Query("date") date: String) : Single<NasaPhoto>

}