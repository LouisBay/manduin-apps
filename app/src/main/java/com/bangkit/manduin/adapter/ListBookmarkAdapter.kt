package com.bangkit.manduin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.R
import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.databinding.ItemRowBookmarkBinding
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton


class ListBookmarkAdapter : ListAdapter<PlaceEntity, ListBookmarkAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onClickCallback: OnClickCallback

    fun setOnClickCallback(onClickCallback: OnClickCallback) {
        this.onClickCallback = onClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder {
        return ListViewHolder(
            ItemRowBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val place = getItem(position) as PlaceEntity
        holder.bind(place)
    }

    inner class ListViewHolder(private val binding : ItemRowBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceEntity) {
            binding.apply {
                tvPlace.text = place.name
                tvLocation.text = String.format("%s, %s", place.city, place.province)

                Glide.with(ivPlace.context)
                    .load(place.imgUrl)
                    .placeholder(R.drawable.image_preview)
                    .error(R.drawable.image_preview)
                    .centerCrop()
                    .into(ivPlace)

                btnBookmark.setOnClickListener { onClickCallback.onBookmarkClicked(place, btnBookmark) }
                root.setOnClickListener { onClickCallback.onItemClicked(place) }
            }
        }
    }

    interface OnClickCallback {
        fun onItemClicked(place: PlaceEntity)
        fun onBookmarkClicked(place: PlaceEntity, button: MaterialButton)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceEntity>() {
            override fun areItemsTheSame(oldPlace: PlaceEntity, newPlace: PlaceEntity): Boolean {
                return oldPlace.placeId == newPlace.placeId
            }

            override fun areContentsTheSame(oldPlace: PlaceEntity, newPlace: PlaceEntity): Boolean {
                return oldPlace == newPlace
            }
        }
    }
}