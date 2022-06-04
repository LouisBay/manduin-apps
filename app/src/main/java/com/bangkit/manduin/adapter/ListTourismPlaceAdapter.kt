package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.R
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.data.remote.response.TourismPlaceItem
import com.bangkit.manduin.databinding.ItemRowNewsBinding
import com.bangkit.manduin.databinding.ItemRowTourismBinding
import com.bangkit.manduin.utils.Helper
import com.bumptech.glide.Glide

class ListTourismPlaceAdapter : RecyclerView.Adapter<ListTourismPlaceAdapter.ListViewHolder>() {
    private val listTourismPlace = ArrayList<TourismPlaceItem>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<TourismPlaceItem>) {
        this.listTourismPlace.clear()
        this.listTourismPlace.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder {
        return ListViewHolder(
            ItemRowTourismBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val place = listTourismPlace[position]
        holder.bind(place)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(place) }
    }

    override fun getItemCount() = listTourismPlace.size

    class ListViewHolder(private val binding : ItemRowTourismBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: TourismPlaceItem) {
            binding.apply {
                tvTourism.text = place.nama
                tvLocation.text = Helper.generateAddressFromLocation(place.lat, place.lon, root.context)

                Glide.with(ivPlace.context)
                    .load(place.imgUrl)
                    .placeholder(R.drawable.image_preview)
                    .error(R.drawable.image_preview)
                    .centerCrop()
                    .into(ivPlace)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(place: TourismPlaceItem)
    }
}