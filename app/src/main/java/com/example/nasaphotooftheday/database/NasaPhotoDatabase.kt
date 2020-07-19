package com.example.nasaphotooftheday.database

import android.content.Context
import androidx.room.*
import com.example.nasaphotooftheday.database.entity.NasaPhoto
import java.util.*

@Database(entities = [NasaPhoto::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NPODDatabase : RoomDatabase(){
    abstract fun npodDao() : NasaPhotoDao

    companion object {
        private var instance: NPODDatabase? = null
        @Synchronized
        fun getInstance(context: Context): NPODDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NPODDatabase::class.java, "npod_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

}


class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
