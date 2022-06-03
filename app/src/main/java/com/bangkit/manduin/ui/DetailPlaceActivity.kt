package com.bangkit.manduin.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListReviewAdapter
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.databinding.ActivityDetailPlaceBinding
import com.bangkit.manduin.ui.main.fragment.HomeFragment
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var reviewAdapter: ListReviewAdapter
    private var idItem: Int = 0
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idItem = intent.getIntExtra(HomeFragment.KEY_LANDMARK, 0)

        actionBar()
        loadReview()
        initComponent()
        observeData()
    }

    private fun initComponent() {
        binding.btnMaps.setOnClickListener {
            startActivity(Intent(this@DetailPlaceActivity, MapsActivity::class.java))
        }

        binding.btnDirections.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=-6.98389,110.4104"))
            startActivity(intent)
        }
        binding.btnAddReview.setOnClickListener {
            Toast.makeText(applicationContext, resources.getString(R.string.toast_review), Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeData() {
        if (idItem != 0) {
            detailViewModel.getLandmarkDetail(idItem).observe(this) { result ->
                showResult(result)
            }
        }
    }

    private fun showResult(result: Result<LandmarkItem>?) {
        when (result) {
            is Result.Loading -> { }
            is Result.Success -> {
                val data = result.data
                parseData(data)
            }
            is Result.Error -> {
                Toast.makeText(applicationContext, resources.getString(R.string.error_occured, result.errorMessage), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun parseData(data: LandmarkItem) {
        val rating = data.rating
        val ratingResult = rating/10f

        binding.apply {
            actionBar.tvTitle.text = data.nama
            tvDescLandmark.text = data.description
            tvRating.text = ratingResult.toString()

            Glide.with(applicationContext)
                .load(data.imgUrl)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .centerCrop()
                .into(ivDetail)

            CoroutineScope(Dispatchers.Default).launch {
                for (i in 1 until rating + 1) {
                    ratingBar.progress = i
                    delay(5)
                }
            }
        }
    }

    private fun actionBar() {
        val btnBack: Button = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun loadReview() {
        reviewAdapter = ListReviewAdapter()
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = reviewAdapter
        val listReview = DataDummy.generateDummyReview()
        reviewAdapter.setList(listReview)
    }
}