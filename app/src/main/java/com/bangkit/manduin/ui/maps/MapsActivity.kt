package com.bangkit.manduin.ui.maps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bangkit.manduin.R
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.MapsTourismPlaceItem
import com.bangkit.manduin.databinding.ActivityMapsBinding
import com.bangkit.manduin.ui.detail.DetailPlaceActivity
import com.bangkit.manduin.utils.Constant
import com.bangkit.manduin.utils.CustomInfoMarkerMaps
import com.bangkit.manduin.utils.Helper
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.MapsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var idLandmark: Int = 0
    private var currentLocation: LatLng? = null
    private lateinit var landmark: LandmarkItem

    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get extra from intent
        idLandmark = intent.getIntExtra(Constant.TAG_LANDMARK, 0)

        initComponent()

        loadingDialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isMapToolbarEnabled = true
            isCompassEnabled = false
            isMyLocationButtonEnabled = false
        }

        mMap.setInfoWindowAdapter(CustomInfoMarkerMaps(this))
        mMap.setOnMarkerClickListener(this)
        mMap.setOnInfoWindowClickListener(this)


        observeData()
        getMyLocation()
        setMapStyle()
    }


    private fun initComponent() {
        // Set Map Fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set Loading Dialog
        val builder = MaterialAlertDialogBuilder(this)
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
        loadingDialog = builder.create()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnBack.setOnClickListener { super.onBackPressed() }
        binding.btnMyLocation.setOnClickListener {
            getMyLocation()
            if (currentLocation != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
            }
        }
    }

    private fun observeData() {
        mapsViewModel.apply {
            getLandmark(idLandmark)
            resultLandmark.observe(this@MapsActivity) { result ->
                processLandmarkResult(result)
            }

            resultWisataMaps.observe(this@MapsActivity) { result ->
                processWisataResult(result)
            }
        }
    }

    private fun processWisataResult(result: Result<ArrayList<MapsTourismPlaceItem>>?) {
        when(result) {
            is Result.Loading -> { showLoading(true) }
            is Result.Success -> {
                showWisataMarker(result.data)
                showLoading(false)
            }
            is Result.Error -> {
                Toast.makeText(applicationContext, resources.getString(R.string.failed_get_data_maps), Toast.LENGTH_LONG).show()
                showLoading(false)
            }
            else -> {}
        }
    }

    private fun processLandmarkResult(result: Result<LandmarkItem>?) {
        when(result) {
            is Result.Loading -> { showLoading(true) }
            is Result.Success -> {
                landmark = result.data
                showLandmarkMarker(landmark)
                mapsViewModel.getNearestTourismLocFromLandmark(result.data.label)
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
        val address = Helper.generateAddressFromLocation(data.lat, data.lon, this)
        val snippets = String.format("%s|%s", address, data.imgUrl)
        mMap.addMarker(
            MarkerOptions()
                .position(landmarkLocation)
                .title(data.nama)
                .snippet(snippets)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_landmark))
        )?.tag = arrayListOf(data.landId, Constant.TAG_LANDMARK)

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(landmarkLocation, 15F))

    }

    private fun showWisataMarker(data: ArrayList<MapsTourismPlaceItem>) {
        data.forEach {
            val wisataLocation = LatLng(it.lat, it.lon)
            val address = Helper.generateAddressFromLocation(it.lat, it.lon, this)
            val snippets = String.format("%s|%s", address, it.imgUrl)
            mMap.addMarker(
                MarkerOptions()
                    .position(wisataLocation)
                    .title(it.namaWisata)
                    .snippet(snippets)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_place))
            )?.tag = arrayListOf(it.placeId, Constant.TAG_TOURISM_PLACE)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        CoroutineScope(Dispatchers.Main).launch {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
        }
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {
        val markerTag = marker.tag as ArrayList<*>
        val tagIntent = markerTag[1].toString()
        val id = markerTag[0] as Int

        if (tagIntent == Constant.TAG_LANDMARK) {
            Intent(this@MapsActivity, DetailPlaceActivity::class.java).apply {
                putExtra(Constant.EXTRA_DETAIL, tagIntent)
                putExtra(Constant.TAG_LANDMARK, id)
                Log.d(TAG, "tagIntent:${tagIntent}, id:${id}")
            }.also { startActivity(it) }
        } else {
            Intent(this@MapsActivity, DetailPlaceActivity::class.java).apply {
                putExtra(Constant.EXTRA_DETAIL, tagIntent)
                putExtra(Constant.TAG_TOURISM_PLACE, id)
                Log.d(TAG, "tagIntent:${tagIntent}, id:${id}")
            }.also { startActivity(it) }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> { getMyLocation() }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> { getMyLocation() }
                else -> {
                    Toast.makeText(applicationContext, resources.getString(R.string.location_not_granted), Toast.LENGTH_LONG).show()
                }
            }
        }

    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mMap.isMyLocationEnabled = true
            binding.btnMyLocation.visibility = View.VISIBLE
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                } else {
                    Toast.makeText(applicationContext, resources.getString(R.string.location_not_found), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            binding.btnMyLocation.visibility = View.GONE
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun checkPermission(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}