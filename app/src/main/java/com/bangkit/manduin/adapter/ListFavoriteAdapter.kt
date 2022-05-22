package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowBookmarkBinding
import com.bangkit.manduin.databinding.ItemRowProvinceBinding
import com.bangkit.manduin.model.FavoriteModel
import com.bangkit.manduin.model.ProvinceModel
import com.bumptech.glide.Glide
import java.util.ArrayList

class ListFavoriteAdapter : RecyclerView.Adapter<ListFavoriteAdapter.ItemTarget>() {
    private val listFavorite = ArrayList<FavoriteModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listFavorite : List<FavoriteModel>) {
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }

    inner class ItemTarget(private val binding : ItemRowBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : FavoriteModel) {
            Glide.with(itemView.context)
                .load(model.image)
                .centerCrop()
                .into(binding.ivPlace)
            binding.tvPlace.text = model.place
            binding.tvLocation.text = model.location
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ItemTarget {
        return ItemTarget(
            ItemRowBookmarkBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ItemTarget, position : Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount() : Int = listFavorite.size
}