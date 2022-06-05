package com.bangkit.manduin.ui.detail

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListCommentAdapter
import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.TourismPlaceItem
import com.bangkit.manduin.databinding.ActivityDetailPlaceBinding
import com.bangkit.manduin.ui.maps.MapsActivity
import com.bangkit.manduin.utils.Constant
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.utils.Helper
import com.bangkit.manduin.utils.Helper.toPlaceEntity
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailPlaceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var reviewAdapter: ListCommentAdapter
    private var idItem: Int = 0
    private var tag: String = ""
    private var placeEntity: PlaceEntity? = null
    private var isPlaceBookmarked:Boolean = false
    private lateinit var location: LatLng
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtraIntent()
        actionBar()
        initComponent()
        observeData()
        loadReview()
    }

    private fun getExtraIntent() {
        tag = intent.getStringExtra(Constant.EXTRA_DETAIL).toString()

        idItem = if (tag == Constant.TAG_LANDMARK) {
            intent.getIntExtra(Constant.TAG_LANDMARK, 0)
        } else {
            intent.getIntExtra(Constant.TAG_TOURISM_PLACE, 0)
        }
    }

    private fun initComponent() {
        binding.apply {
            btnMaps.setOnClickListener(this@DetailPlaceActivity)
            btnDirections.setOnClickListener(this@DetailPlaceActivity)
            btnAddComment.setOnClickListener(this@DetailPlaceActivity)
            btnBookmark.setOnClickListener(this@DetailPlaceActivity)
        }
    }

    private fun observeData() {
        if (idItem != 0) {
            detailViewModel.apply {
                if (tag == Constant.TAG_LANDMARK) {
                    getLandmarkDetail(idItem)
                    resultLandmark.observe(this@DetailPlaceActivity) { result ->
                        showResult(result)
                    }
                } else {
                    getTourismPlaceDetail(idItem)
                    resultTourismPlace.observe(this@DetailPlaceActivity) { result ->
                        showResult(result)
                    }
                }

                checkBookmarked(idItem)
                isBookmarked.observe(this@DetailPlaceActivity) {
                    isPlaceBookmarked = it
                    setBookmarkButton(it)
                }
            }
        }
    }

    private fun setBookmarkButton(isBookmarked: Boolean) {
        binding.btnBookmark.apply {
            if (isBookmarked) setIconResource(R.drawable.bookmarked)
            else setIconResource(R.drawable.bookmark)
        }
    }

    private fun showResult(result: Result<Any>?) {
        when (result) {
            is Result.Loading -> { setDetailShimmer(true) }
            is Result.Success -> {
                if (tag == Constant.TAG_LANDMARK) parseDataLandmark(result.data as LandmarkItem)
                else parseDataTourismPlace(result.data as TourismPlaceItem)

                lifecycleScope.launch {
                    delay(2000L)
                    setDetailShimmer(false)
                }
            }
            is Result.Error -> {
                Toast.makeText(applicationContext, resources.getString(R.string.error_occured, result.errorMessage), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun parseDataLandmark(data: LandmarkItem) {
        location = LatLng(data.lat, data.lon)
        val rating = data.rating

        binding.apply {
            btnBookmark.visibility = View.GONE
            btnMaps.visibility = View.VISIBLE
            btnDirections.visibility = View.GONE
            actionBar.tvTitle.text = data.nama
            tvDescLandmark.text = data.description
            chipCategory.text = resources.getString(R.string.landmark)
            chipCategoryPlace.text = data.category
            tvAddress.text = Helper.generateAddressFromLocation(data.lat, data.lon, applicationContext)

            Glide.with(applicationContext)
                .load(data.imgUrl)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .centerCrop()
                .into(ivDetail)

            lifecycleScope.launch(Dispatchers.Default) {
                delay(2000L)
                for (i in 1 until rating + 1) {
                    val ratingResult = i/10f
                    withContext(Dispatchers.Main) {
                        ratingBar.progress = i
                        tvRating.text = ratingResult.toString()
                    }
                    delay(10)
                }
            }
        }
    }

    private fun parseDataTourismPlace(data: TourismPlaceItem) {
        placeEntity = data.toPlaceEntity(this@DetailPlaceActivity)
        location = LatLng(data.lat, data.lon)
        val rating = data.rating

        binding.apply {
            btnMaps.visibility = View.GONE
            btnDirections.visibility = View.VISIBLE
            actionBar.tvTitle.text = data.nama
            tvDescLandmark.text = data.description
            chipCategory.text = resources.getString(R.string.tourism)
            chipCategoryPlace.text = data.category
            tvAddress.text = Helper.generateAddressFromLocation(data.lat, data.lon, applicationContext)

            Glide.with(applicationContext)
                .load(data.imgUrl)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .centerCrop()
                .into(ivDetail)

            lifecycleScope.launch(Dispatchers.Default) {
                delay(2000L)
                for (i in 1 until rating + 1) {
                    val ratingResult = i/10f
                    withContext(Dispatchers.Main) {
                        ratingBar.progress = i
                        tvRating.text = ratingResult.toString()
                    }
                    delay(10)
                }
            }
        }
    }

    private fun actionBar() {
        binding.actionBar.apply {
            tvTitle.isSelected = true
            btnBack.setOnClickListener { super.onBackPressed() }
        }
    }

    private fun loadReview() {
        reviewAdapter = ListCommentAdapter()
        binding.rvComment.layoutManager = LinearLayoutManager(this)
        binding.rvComment.adapter = reviewAdapter
        val listReview = DataDummy.generateDummyReview()
        reviewAdapter.setList(listReview)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_maps -> {
                Intent(this, MapsActivity::class.java).apply {
                    putExtra(Constant.TAG_LANDMARK, idItem)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }.also { startActivity(it) }
            }
            R.id.btn_directions -> {
                val uriString = StringBuilder("http://maps.google.com/maps?daddr=").append("${location.latitude},${location.longitude}")
                Intent(Intent.ACTION_VIEW, Uri.parse(uriString.toString())).also { startActivity(it) }
            }
            R.id.btn_add_comment -> {
                Toast.makeText(applicationContext, resources.getString(R.string.toast_review), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_bookmark -> {
                if(isPlaceBookmarked) {
                    setBookmarkButton(false)
                    showConfirmDialog()
                } else {
                    setBookmarkButton(true)
                    placeEntity?.let { detailViewModel.insertPlaceToBookmark(it) }
                    Toast.makeText(applicationContext, resources.getString(R.string.success_bookmark), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showConfirmDialog() {
        MaterialAlertDialogBuilder(this@DetailPlaceActivity)
            .setTitle(resources.getString(R.string.confirm_delete_bookmark))
            .setMessage(resources.getString(R.string.confirm_delete_bookmark_message))
            .setIcon(R.drawable.ic_baseline_logout_24)
            .setPositiveButton("Yes") { _, _ ->
                placeEntity?.let { detailViewModel.deletePlaceFromBookmark(it) }
                Toast.makeText(applicationContext, resources.getString(R.string.success_unbookmark), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
                setBookmarkButton(true)
            }
            .setCancelable(true)
            .setOnCancelListener {
                setBookmarkButton(true)
            }
            .show()
    }

    private fun setDetailShimmer(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerDetail.visibility = View.VISIBLE
                shimmerDetail.startShimmer()
                container.visibility = View.GONE
                actionBar.tvTitle.visibility = View.GONE
            } else {
                shimmerDetail.visibility = View.GONE
                shimmerDetail.stopShimmer()
                container.visibility = View.VISIBLE
                actionBar.tvTitle.visibility = View.VISIBLE
            }
        }
    }
}
