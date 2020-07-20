package com.example.nasaphotooftheday.source

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class NasaPhotoNetworkDatasourceTest {
    lateinit var apiService: NasaPhotoService
    lateinit var mockWebService: MockWebServer

    @Test
    fun `getPhotoOfTheDay`() {
        mockWebService.enqueue(
            MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(
                getJson("success_response.json")
            )
        )

        val response = apiService.getNasaPhoto().blockingGet()
        assertEquals(response.title, "Rotating Moon from LRO")
    }

    @Test
    fun `getPhotoOfOnDate`() {
        mockWebService.enqueue(
            MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(
                getJson("success_response.json")
            )
        )

        val response = apiService.getNasaPhotoOnDate("2020-07-19").blockingGet()
        assertEquals(response.url, "https://www.youtube.com/embed/sNUNB6CMnE8?rel=0")
    }

    @Before
    fun setup() {
        mockWebService = MockWebServer()
        mockWebService.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebService.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(NasaPhotoService::class.java)
    }

    @After
    fun tearDown(){
        mockWebService.shutdown()
    }

    private fun getJson(path: String): String {
        val uri = this::class.java.classLoader!!.getResource(path)
        val file = File(uri!!.path)
        return String(file.readBytes())
    }
}