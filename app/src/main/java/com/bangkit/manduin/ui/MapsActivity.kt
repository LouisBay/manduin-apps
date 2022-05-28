package com.bangkit.manduin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.manduin.R
import com.bangkit.manduin.databinding.ActivityMapsBinding
import com.bangkit.manduin.utils.CustomInfoMarkerMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val udinus = LatLng(-6.983773510311124, 110.41042618605893)
        mMap.addMarker(
            MarkerOptions()
                .position(udinus)
                .title("Lawang Sewu")
                .snippet("Alamat")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marker)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udinus, 14F))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.setInfoWindowAdapter(CustomInfoMarkerMaps(this))
    }
}