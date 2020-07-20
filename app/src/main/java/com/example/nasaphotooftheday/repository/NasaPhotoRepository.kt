package com.example.nasaphotooftheday.repository

import com.example.nasaphotooftheday.database.entity.NasaPhoto
import com.example.nasaphotooftheday.source.NasaPhotoClient
import com.example.nasaphotooftheday.source.NasaPhotoNetworkDatasource
import io.reactivex.Single

class NasaPhotoRepository {
    private val apiService = NasaPhotoClient.getClient()
    private val nasaPhotoNetworkDatasource = NasaPhotoNetworkDatasource(apiService)

    fun fetchNasaPhotoDetails(): Single<NasaPhoto> {
        return nasaPhotoNetworkDatasource.getNasaPhotoDetails()
    }

    fun fetchNasaPhotoOnDateDetails(date : String) : Single<NasaPhoto>{
        return nasaPhotoNetworkDatasource.getNasaPhotoOnDateDetails(date)
    }
}