package com.bangkit.manduin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.manduin.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoMarkerMaps (private val context: Context) : GoogleMap.InfoWindowAdapter {

    @SuppressLint("InflateParams")
    override fun getInfoWindow(marker: Marker): View? {
        val view = (context as AppCompatActivity)
            .layoutInflater
            .inflate(R.layout.custom_marker_maps, null)

        val place:TextView = view.findViewById(R.id.tv_place)
        val location:TextView = view.findViewById(R.id.tv_location)
        val image: ImageView = view.findViewById(R.id.iv_place)

        place.text = marker.title
        location.text = marker.snippet
        image.setImageResource(R.drawable.image_preview)
        return view
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}