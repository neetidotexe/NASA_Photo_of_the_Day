package com.example.nasaphotooftheday.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.nasaphotooftheday.database.entity.NasaPhoto

@Dao
interface  NasaPhotoDao {

    @Query("select * from npod")
    fun getNPOD() : LiveData<NasaPhoto>

}