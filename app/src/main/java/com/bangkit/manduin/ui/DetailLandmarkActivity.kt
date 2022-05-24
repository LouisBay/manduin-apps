package com.bangkit.manduin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.DataDummy
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListProvinceAdapter
import com.bangkit.manduin.adapter.ListReviewAdapter
import com.bangkit.manduin.databinding.ActivityDetailLandmarkBinding

class DetailLandmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLandmarkBinding

    private lateinit var reviewAdapter: ListReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLandmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadReview()
    }

    private fun loadReview() {
        reviewAdapter = ListReviewAdapter()
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = reviewAdapter
        val listReview = DataDummy.generateDummyReview()
        reviewAdapter.setList(listReview)
    }
}