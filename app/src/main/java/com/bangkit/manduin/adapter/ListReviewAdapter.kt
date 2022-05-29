package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowReviewBinding
import com.bangkit.manduin.model.ReviewModel
import com.bumptech.glide.Glide
import java.util.ArrayList

class ListReviewAdapter : RecyclerView.Adapter<ListReviewAdapter.ItemTarget>() {
    private val listReview = ArrayList<ReviewModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listReview : List<ReviewModel>) {
        this.listReview.clear()
        this.listReview.addAll(listReview)
        notifyDataSetChanged()
    }

    inner class ItemTarget(private val binding : ItemRowReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : ReviewModel) {
            Glide.with(itemView.context)
                .load(model.image)
                .circleCrop()
                .into(binding.ivUser)
            binding.tvUser.text = model.name
            binding.tvReview.text = model.review
            binding.tvDate.text = model.date
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ItemTarget {
        return ItemTarget(
            ItemRowReviewBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ItemTarget, position : Int) {
        holder.bind(listReview[position])
    }

    override fun getItemCount() : Int = listReview.size
}