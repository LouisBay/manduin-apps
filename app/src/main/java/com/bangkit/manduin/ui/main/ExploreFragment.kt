package com.bangkit.manduin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.utils.DataDummy
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListProvinceAdapter
import com.bangkit.manduin.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var provinceAdapter: ListProvinceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleActionBar: TextView = view.findViewById(R.id.tv_title)
        titleActionBar.text = resources.getString(R.string.explore)

        loadProvince()
    }

    private fun loadProvince() {
        provinceAdapter = ListProvinceAdapter()
        binding.rvProvince.layoutManager = LinearLayoutManager(context)
        binding.rvProvince.adapter = provinceAdapter
        val listProvince = DataDummy.generateDummyProvince()
        provinceAdapter.setList(listProvince)
    }
}