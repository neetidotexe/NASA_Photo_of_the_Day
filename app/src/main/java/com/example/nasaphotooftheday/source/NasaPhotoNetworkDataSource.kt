package com.example.nasaphotooftheday.source

import com.example.nasaphotooftheday.database.entity.NasaPhoto
import io.reactivex.Single

class NasaPhotoNetworkDatasource(private val apiService: NasaPhotoService) {
    fun getNasaPhotoDetails() : Single<NasaPhoto> {
        return apiService.getNasaPhoto()
    }

    fun getNasaPhotoOnDateDetails(date:String) : Single<NasaPhoto>{
        return apiService.getNasaPhotoOnDate(date)
    }
}