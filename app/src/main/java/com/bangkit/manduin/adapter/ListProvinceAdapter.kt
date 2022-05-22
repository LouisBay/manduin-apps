package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowProvinceBinding
import com.bangkit.manduin.model.ProvinceModel
import com.bumptech.glide.Glide
import java.util.ArrayList

class ListProvinceAdapter : RecyclerView.Adapter<ListProvinceAdapter.ItemTarget>() {
    private val listProvince = ArrayList<ProvinceModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listProvince : List<ProvinceModel>) {
        this.listProvince.clear()
        this.listProvince.addAll(listProvince)
        notifyDataSetChanged()
    }

    inner class ItemTarget(private val binding : ItemRowProvinceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : ProvinceModel) {
            Glide.with(itemView.context)
                .load(model.image)
                .centerCrop()
                .into(binding.ivProvince)
            binding.tvProvince.text = model.province
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ItemTarget {
        return ItemTarget(
            ItemRowProvinceBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ItemTarget, position : Int) {
        holder.bind(listProvince[position])
    }

    override fun getItemCount() : Int = listProvince.size
}