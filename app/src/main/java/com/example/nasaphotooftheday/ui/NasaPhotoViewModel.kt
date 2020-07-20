package com.example.nasaphotooftheday.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaphotooftheday.repository.NasaPhotoRepository
import com.example.nasaphotooftheday.database.entity.NasaPhoto
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NasaPhotoViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val nasaPhotoRepository = NasaPhotoRepository()

    private var _nasaPhoto = MutableLiveData<NasaPhoto>()
    val nasaPhoto: LiveData<NasaPhoto>
        get() = _nasaPhoto

    @SuppressLint("CheckResult")
    fun fetchNasaPhotoDetails(){
        compositeDisposable.add(
            nasaPhotoRepository.fetchNasaPhotoDetails()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _nasaPhoto.postValue(it)
                    },
                    {

                    }
                )
        )
    }

    @SuppressLint("CheckResult")
    fun fetchNasaPhotoOnDateDetails(date:String){
        compositeDisposable.add(
            nasaPhotoRepository.fetchNasaPhotoOnDateDetails(date)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _nasaPhoto.postValue(it)
                    },
                    {

                    }
                )
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}