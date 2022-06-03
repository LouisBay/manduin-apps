package com.bangkit.manduin.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.manduin.R
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.databinding.ActivityMapsBinding
import com.bangkit.manduin.ui.main.CameraActivity
import com.bangkit.manduin.utils.CustomInfoMarkerMaps
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var loadingDialog: AlertDialog

    private var idLandmark: Int = 0
    private lateinit var landmark: LandmarkItem

    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get extra from intent
        idLandmark = intent.getIntExtra(CameraActivity.ID_LANDMARK, 0)

        initComponent()

        loadingDialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mMap.setInfoWindowAdapter(CustomInfoMarkerMaps(this))

        observeData()
    }


    private fun initComponent() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnBack.setOnClickListener { super.onBackPressed() }

        val builder = MaterialAlertDialogBuilder(this)
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()

    }

    private fun observeData() {
        mapsViewModel.apply {
            resultLandmark.observe(this@MapsActivity) { result ->
                processLandmarkResult(result)
            }
        }
    }

    private fun processLandmarkResult(result: Result<LandmarkItem>?) {
        when(result) {
            is Result.Loading -> { showLoading(true) }
            is Result.Success -> {
                showLandmarkMarker(result.data)
                showLoading(false)
            }
            is Result.Error -> {
                Toast.makeText(applicationContext, resources.getString(R.string.failed_get_data_maps), Toast.LENGTH_LONG).show()
                showLoading(false)
            }
            else -> {}
        }
    }

    private fun showLandmarkMarker(data: LandmarkItem) {
        val landmarkLocation = LatLng(data.lat, data.lon)

        mMap.addMarker(
            MarkerOptions()
                .position(landmarkLocation)
                .title(data.nama)
                .snippet("${data.lat},${data.lon}")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marker)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(landmarkLocation, 14F))

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.show()
        else loadingDialog.dismiss()
    }
}