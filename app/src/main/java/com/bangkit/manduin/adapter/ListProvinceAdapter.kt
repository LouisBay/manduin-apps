package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowProvinceBinding
import com.bangkit.manduin.model.ProvinceModel
import com.bangkit.manduin.ui.tourism.TourismActivity
import com.bangkit.manduin.utils.Constant
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

            binding.root.setOnClickListener {
                Intent(itemView.context, TourismActivity::class.java).apply {
                    putExtra(Constant.EXTRA_PROVINCE, model.province)
                }.also { intent ->
                    it.context.startActivity(intent)
                }
            }
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