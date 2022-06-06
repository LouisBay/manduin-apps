package com.bangkit.manduin.ui.tourism

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListTourismPlaceAdapter
import com.bangkit.manduin.data.remote.response.TourismPlaceItem
import com.bangkit.manduin.databinding.ActivityTourismBinding
import com.bangkit.manduin.ui.detail.DetailPlaceActivity
import com.bangkit.manduin.utils.Constant
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.TourismViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class TourismActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTourismBinding
    private val tourismViewModel: TourismViewModel by viewModels()
    private var province: String = ""
    private lateinit var tourismAdapter: ListTourismPlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        province = intent.getStringExtra(Constant.EXTRA_PROVINCE).toString()

        initComponent()
        setRvTourism()
        observeData()
    }

    private fun initComponent() {
        binding.apply {
            showLottie(true)
            actionBar.tvTitle.text = province
            actionBar.tvTitle.isSelected = true
            actionBar.btnBack.setOnClickListener { super.onBackPressed() }
        }
    }

    private fun observeData() {
        tourismViewModel.apply {
            resultListWisata.observe(this@TourismActivity) { result ->
                showResult(result)
            }
        }
    }

    private fun setRvTourism() {
        tourismAdapter = ListTourismPlaceAdapter()

        binding.rvTourism.apply {
            layoutManager = LinearLayoutManager(this@TourismActivity)
            adapter = tourismAdapter
        }

        tourismAdapter.setOnItemClickCallback(object : ListTourismPlaceAdapter.OnItemClickCallback{
            override fun onItemClicked(place: TourismPlaceItem) {
                Intent(this@TourismActivity, DetailPlaceActivity::class.java).apply {
                    putExtra(Constant.EXTRA_DETAIL, Constant.TAG_TOURISM_PLACE)
                    putExtra(Constant.TAG_TOURISM_PLACE, place.placeId)
                }.also { startActivity(it) }
            }
        })

        tourismViewModel.isDataFetched.observe(this) { isFetched ->
            if (!isFetched) tourismViewModel.getTourismPlaceAtProvince(province)
        }
    }

    private fun showResult(result: Result<ArrayList<TourismPlaceItem>>?) {
        when (result) {
            is Result.Loading -> { setShimmer(true) }
            is Result.Success -> {
                tourismAdapter.setList(result.data)
                lifecycleScope.launch {
                    delay(2000L)
                    setShimmer(false)
                }

                tourismViewModel.dataFetched()
            }
            is Result.Error -> {
                try {
                    var message = result.errorMessage.split("|")[0]
                    val code = result.errorMessage.split("|")[1].toInt()

                    if (code == 400) {
                        setShimmer(false)
                        showLottie(false)
                        message = resources.getString(R.string.msg_available_soon)
                    }

                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, result.errorMessage, Toast.LENGTH_SHORT).show()
                }

            }
            else -> {}
        }
    }

    private fun showLottie(state : Boolean) {
        binding.lottieSoon.visibility = if (state) View.GONE else View.VISIBLE
        binding.tvAvailableSoon.visibility = if (state) View.GONE else View.VISIBLE
        binding.tvDesc.visibility = if (state) View.GONE else View.VISIBLE
    }

    private fun setShimmer(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerTourism.visibility = View.VISIBLE
                shimmerTourism.startShimmer()
                rvTourism.visibility = View.GONE
            } else {
                shimmerTourism.visibility = View.GONE
                shimmerTourism.stopShimmer()
                rvTourism.visibility = View.VISIBLE
            }
        }
    }
}