package com.example.nasaphotooftheday.source

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "DEMO_KEY"
const val BASE_URL = "https://api.nasa.gov/"

object NasaPhotoClient {

    fun getClient(): NasaPhotoService {
        val requestInterceptor = Interceptor {
            val url = it.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("thumbs", "true")
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaPhotoService::class.java)
    }
}