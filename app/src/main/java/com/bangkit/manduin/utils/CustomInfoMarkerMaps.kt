package com.bangkit.manduin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.manduin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

        val snippets = Helper.parseSnippet(marker.snippet.toString())

        place.text = marker.title
        location.text = snippets[0]

        Glide.with(context)
            .load(snippets[1])
            .placeholder(R.drawable.image_preview)
            .error(R.drawable.image_preview)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    if (marker.isInfoWindowShown) {
                        marker.hideInfoWindow()
                        marker.showInfoWindow()
                    }
                    return false
                }
            })
            .into(image)

        return view
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}