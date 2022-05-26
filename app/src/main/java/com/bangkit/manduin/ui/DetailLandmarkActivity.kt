package com.bangkit.manduin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListReviewAdapter
import com.bangkit.manduin.databinding.ActivityDetailLandmarkBinding

class DetailLandmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLandmarkBinding

    private lateinit var reviewAdapter: ListReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLandmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar()
        loadReview()
    }

    private fun actionBar() {
        val place: TextView = findViewById(R.id.tv_title)
        val btnBack: Button = findViewById(R.id.btn_back)
        val btnMaps : Button = findViewById(R.id.btn_maps)

        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        btnMaps.setOnClickListener {
            startActivity(Intent(this@DetailLandmarkActivity, MapsActivity::class.java))
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