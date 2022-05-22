package com.bangkit.manduin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.manduin.DataDummy
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListFavoriteAdapter
import com.bangkit.manduin.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: ListFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleActionBar: TextView = view.findViewById(R.id.tv_title)
        titleActionBar.text = resources.getString(R.string.bookmark)

        loadFavorite()
    }

    private fun loadFavorite() {
        favoriteAdapter = ListFavoriteAdapter()
        binding.rvFavorite.layoutManager = GridLayoutManager(context, 2)
        binding.rvFavorite.adapter = favoriteAdapter
        val listFavorite = DataDummy.generateDummyFavorite()
        favoriteAdapter.setList(listFavorite)
    }

}