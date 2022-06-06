package com.bangkit.manduin.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListBookmarkAdapter
import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.databinding.FragmentBookmarkBinding
import com.bangkit.manduin.ui.detail.DetailPlaceActivity
import com.bangkit.manduin.utils.Constant
import com.bangkit.manduin.viewmodel.BookmarkViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookmarkAdapter: ListBookmarkAdapter
    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionBar.tvTitle.text = resources.getString(R.string.bookmark)

        setRvFavorite()
        observerDataFavorite()
    }

    private fun observerDataFavorite() {
        bookmarkViewModel.getAllBookmarkedPlace().observe(viewLifecycleOwner) { data ->
            Log.d("BookmarkFragment", data.toString())
            bookmarkAdapter.submitList(data)
            if (data.isNotEmpty()) {
                showLottie(true)
            } else {
                showLottie(false)
            }
        }
    }

    private fun setRvFavorite() {
        bookmarkAdapter = ListBookmarkAdapter()

        binding.rvBookmark.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = bookmarkAdapter
        }

        bookmarkAdapter.setOnClickCallback(object : ListBookmarkAdapter.OnClickCallback{
            override fun onItemClicked(place: PlaceEntity) {
                Intent(requireActivity(), DetailPlaceActivity::class.java).apply {
                    putExtra(Constant.EXTRA_DETAIL, Constant.TAG_TOURISM_PLACE)
                    putExtra(Constant.TAG_TOURISM_PLACE, place.placeId)
                }.also { startActivity(it) }
            }

            override fun onBookmarkClicked(place: PlaceEntity, button: MaterialButton) {
                button.setIconResource(R.drawable.bookmark)
                showConfirmDialog(place, button)
            }
        })
    }

    private fun showConfirmDialog(place: PlaceEntity, button: MaterialButton) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.confirm_delete_bookmark))
            .setMessage(resources.getString(R.string.confirm_delete_bookmark_message))
            .setIcon(R.drawable.ic_baseline_logout_24)
            .setPositiveButton("Yes") { _, _ ->
                bookmarkViewModel.deletePlaceFromBookmark(place)
                Toast.makeText(requireContext(), resources.getString(R.string.success_unbookmark), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
                button.setIconResource(R.drawable.bookmarked)
            }
            .setCancelable(true)
            .setOnCancelListener {
                button.setIconResource(R.drawable.bookmarked)
            }
            .show()
    }

    private fun showLottie(state : Boolean) {
        binding.llLottieGrup.visibility = if (state) View.GONE else View.VISIBLE
    }

}