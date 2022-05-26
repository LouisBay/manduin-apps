package com.bangkit.manduin.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.manduin.*
import com.bangkit.manduin.adapter.ListNewsAdapter
import com.bangkit.manduin.adapter.SliderAdapter
import com.bangkit.manduin.databinding.FragmentHomeBinding
import com.bangkit.manduin.model.SliderItemDestinationModel
import com.bangkit.manduin.ui.AllNewsActivity
import com.bangkit.manduin.utils.DataDummy
import java.lang.Math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var handler : Handler
    private lateinit var listSliderItemDestination: ArrayList<SliderItemDestinationModel>
    private lateinit var adapter: SliderAdapter
    private lateinit var newsAdapter: ListNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setupTransformer()
        loadNews()
        toAllNews()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })
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

    private fun init(view: View){
        viewPager = view.findViewById(R.id.vp_destination)

        handler = Handler(Looper.myLooper()!!)
        listSliderItemDestination = ArrayList()

        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide1,
            resources.getString(R.string.lawang_sewu),
            resources.getString(R.string.loc_lawang_sewu))
        )
        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide2,
            resources.getString(R.string.tugu_jogja),
            resources.getString(R.string.loc_tugu_jogja))
        )
        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide3,
            resources.getString(R.string.masjid_agung),
            resources.getString(R.string.loc_masjid_agung))
        )
        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide4,
            resources.getString(R.string.jogja_kembali),
            resources.getString(R.string.loc_jogja_kembali))
        )
        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide5,
            resources.getString(R.string.borobudur),
            resources.getString(R.string.loc_borobudur))
        )
        listSliderItemDestination.add(
            SliderItemDestinationModel(R.drawable.slide6,
            resources.getString(R.string.prambanan),
            resources.getString(R.string.loc_prambanan))
        )

        adapter = SliderAdapter(listSliderItemDestination, viewPager)

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun loadNews() {
        newsAdapter = ListNewsAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.adapter = newsAdapter
        val listNews = DataDummy.generateDummyNews()
        newsAdapter.setList(listNews)
    }

    private fun toAllNews() {
        binding.tvAllNews.setOnClickListener {
            startActivity(Intent(requireActivity(), AllNewsActivity::class.java))
        }
    }
}