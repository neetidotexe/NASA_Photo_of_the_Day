package com.example.nasaphotooftheday.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasaphotooftheday.R
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NasaPhotoViewModel
    lateinit var titleText: TextView
    lateinit var nasaPhotoImage: ImageView
    lateinit var descriptionText: TextView
    lateinit var zoomImage: ImageView
    lateinit var cancelImage: ImageView
    lateinit var calendarButton: ImageView
    lateinit var dateSelected: String
    lateinit var mediaUrl: String
    var isMediaVideo: Boolean = false
    var datePicker: DatePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NasaPhotoViewModel::class.java)
        titleText = findViewById(R.id.title)
        nasaPhotoImage = findViewById(R.id.nasaPhoto)
        descriptionText = findViewById(R.id.description)
        zoomImage = findViewById(R.id.zoom)
        cancelImage = findViewById(R.id.cancel)
        calendarButton = findViewById(R.id.calendarButton)

        //to make description text scrollable
        descriptionText.movementMethod = ScrollingMovementMethod()
        titleText.movementMethod = ScrollingMovementMethod()

        //zoom the image
        zoomImage.setOnClickListener {
            if(!isMediaVideo){
                titleText.visibility = View.INVISIBLE
                descriptionText.visibility = View.INVISIBLE
                zoomImage.visibility = View.INVISIBLE
                calendarButton.visibility = View.INVISIBLE
                cancelImage.visibility = View.VISIBLE
            }else{
                openYoutubeLink(mediaUrl)
            }

        }

        //come back from zoom
        cancelImage.setOnClickListener {
            titleText.visibility = View.VISIBLE
            descriptionText.visibility = View.VISIBLE
            zoomImage.visibility = View.VISIBLE
            calendarButton.visibility = View.VISIBLE
            cancelImage.visibility = View.GONE
        }

        //select a particular day to show photo on that day
        calendarButton.setOnClickListener {
            onDateClick()
        }

        viewModel.fetchNasaPhotoDetails()

        viewModel.nasaPhoto.observe(this, Observer {

            titleText.text = it.title

            if (it.media_type == "image") {
                zoomImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_zoom))
                isMediaVideo = false
                try {
                    Glide.with(this)
                        .load(it.hdurl)
                        .into(nasaPhotoImage)
                } catch (ex: Exception) {
                }
            } else {
                zoomImage.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_play))
                isMediaVideo = true
                mediaUrl = it.url.toString()
                try {
                    Glide.with(this)
                        .load(it.thumbnail_url)
                        .centerCrop()
                        .into(nasaPhotoImage)
                } catch (ex: Exception) {

                }
            }

            descriptionText.text = it.description
        })

    }

    private fun onDateClick() {
        val zone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(zone)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year1 = calendar.get(Calendar.YEAR)

        val dpd = DatePickerDialog(
            this,
            R.style.datepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val myMonth = if (monthOfYear < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
                val myDay = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val myYear = "$year"
                dateSelected = "$myYear-$myMonth-$myDay"
                datePicker = DatePicker(this)
                datePicker?.init(year, monthOfYear+1, dayOfMonth, null)
                viewModel.fetchNasaPhotoOnDateDetails(dateSelected)
            },
            year1,
            month,
            day
        )

        dpd.datePicker.minDate = 803260846000
        dpd.datePicker.maxDate = System.currentTimeMillis()
        datePicker?.let{
            dpd.updateDate(it.year, it.month-1, it.dayOfMonth)
        }
        dpd.show()
    }

    private fun openYoutubeLink(url: String){
        val videoId = url.split("/").last().split("?").first()
        val youtubeUrl = getString(R.string.youtube_link_start) + videoId

        if(isAppInstalled("com.google.android.youtube")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }
        else{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(intent)
        }
    }

    private fun isAppInstalled(packageName: String): Boolean {
        val pm = packageManager
        try{
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException){
        }
        return false
    }
}

