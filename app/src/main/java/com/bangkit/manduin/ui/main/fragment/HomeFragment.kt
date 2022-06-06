package com.bangkit.manduin.ui.main.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.manduin.*
import com.bangkit.manduin.adapter.ListNewsAdapter
import com.bangkit.manduin.adapter.LandmarkSliderAdapter
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.databinding.FragmentHomeBinding
import com.bangkit.manduin.ui.news.AllNewsActivity
import com.bangkit.manduin.ui.detail.DetailPlaceActivity
import com.bangkit.manduin.utils.Constant
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var handler : Handler
    private lateinit var listSliderItemDestination: ArrayList<LandmarkItem>
    private lateinit var adapter: LandmarkSliderAdapter
    private lateinit var newsAdapter: ListNewsAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerLandmark()
        setupTransformer()
        setNewsRecyclerView()
        observerData()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })

        binding.tvAllNews.setOnClickListener {
            startActivity(Intent(requireActivity(), AllNewsActivity::class.java))
        }
    }

    private fun observerData() {
        homeViewModel.apply {
            resultlistNews.observe(viewLifecycleOwner) { result ->
                showResultNewsData(result)
            }

            resultlistLandmark.observe(viewLifecycleOwner) { result ->
                showResultLandmarkData(result)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable , 2000)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private val runnable = Runnable {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun setupTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager.setPageTransformer(transformer)
    }

    private fun setViewPagerLandmark(){
        handler = Handler(Looper.myLooper()!!)
        listSliderItemDestination = ArrayList()

        viewPager = binding.vpDestination
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        homeViewModel.isLandmarksFetched.observe(viewLifecycleOwner) { isFetched ->
            if (!isFetched) homeViewModel.getAllLandmark()
        }

    }

    private fun showResultLandmarkData(result: Result<ArrayList<LandmarkItem>>?) {
        when (result) {
            is Result.Loading -> { setLandmarkShimmer(true) }
            is Result.Success -> {
                listSliderItemDestination = result.data
                adapter = LandmarkSliderAdapter(listSliderItemDestination, viewPager)

                adapter.setOnItemClickCallback(object : LandmarkSliderAdapter.OnItemClickCallback {
                    override fun onItemClicked(landmarkItem: LandmarkItem) {
                        Intent(requireContext(), DetailPlaceActivity::class.java).apply {
                            putExtra(Constant.EXTRA_DETAIL, Constant.TAG_LANDMARK)
                            putExtra(Constant.TAG_LANDMARK, landmarkItem.landId)
                        }.also { requireActivity().startActivity(it) }
                    }
                })

                viewPager.adapter = adapter

                lifecycleScope.launch {
                    delay(2000L)
                    setLandmarkShimmer(false)
                }

                homeViewModel.landmarksFetched()
            }
            is Result.Error -> {
                Toast.makeText(requireContext(), resources.getString(R.string.error_occured, result.errorMessage), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }


    private fun setNewsRecyclerView() {
        newsAdapter = ListNewsAdapter()

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        newsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(news: NewsItem) {
                if (news.link == null) {
                    Toast.makeText(requireContext(), resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                } else {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(news.link.toString())
                    }.also { startActivity(it) }
                }
            }
        })

        homeViewModel.isNewsFetched.observe(viewLifecycleOwner) { isFetched ->
            if (!isFetched) homeViewModel.getTravelNewsData()
        }
    }

    private fun showResultNewsData(result: Result<ArrayList<NewsItem>>?) {
        when (result) {
            is Result.Loading -> { setNewsShimmer(true) }
            is Result.Success -> {
                newsAdapter.setList(result.data, ListNewsAdapter.TAG_HOME)

                lifecycleScope.launch {
                    delay(2000L)
                    setNewsShimmer(false)
                }

                homeViewModel.newsFetched()
            }
            is Result.Error -> {
                Toast.makeText(requireContext(), resources.getString(R.string.error_occured, result.errorMessage), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun setNewsShimmer(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerNews.visibility = View.VISIBLE
                shimmerNews.startShimmer()
                rvNews.visibility = View.GONE
            } else {
                shimmerNews.visibility = View.GONE
                shimmerNews.stopShimmer()
                rvNews.visibility = View.VISIBLE
            }
        }
    }

    private fun setLandmarkShimmer(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerLandmark.visibility = View.VISIBLE
                shimmerLandmark.startShimmer()
                vpDestination.visibility = View.GONE
            } else {
                shimmerLandmark.visibility = View.GONE
                shimmerLandmark.stopShimmer()
                vpDestination.visibility = View.VISIBLE
            }
        }
    }
}