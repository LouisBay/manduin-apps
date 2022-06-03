package com.bangkit.manduin.ui

import android.content.Intent
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
        observeData()
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
        binding.apply {
            actionBar.tvTitle.text = data.nama
            tvDescLandmark.text = data.description

            Glide.with(applicationContext)
                .load(data.imgUrl)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .centerCrop()
                .into(ivDetail)
        }
    }

    private fun actionBar() {
        val btnBack: Button = findViewById(R.id.btn_back)
        val btnMaps : Button = findViewById(R.id.btn_maps)

        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnMaps.setOnClickListener {
            startActivity(Intent(this@DetailPlaceActivity, MapsActivity::class.java))
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