package com.example.nasaphotooftheday.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
            titleText.visibility = View.INVISIBLE
            descriptionText.visibility = View.INVISIBLE
            zoomImage.visibility = View.INVISIBLE
            calendarButton.visibility = View.INVISIBLE
            cancelImage.visibility = View.VISIBLE
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
                try {
                    Glide.with(this)
                        .load(it.hdurl)
                        .into(nasaPhotoImage)
                } catch (ex: Exception) {
                }
            } else {
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
                viewModel.fetchNasaPhotoOnDateDetails(dateSelected)
            },
            year1,
            month,
            day
        )

        dpd.datePicker.minDate = 803260846000
        dpd.show()


    }
}

