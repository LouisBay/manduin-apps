package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowCommentBinding
import com.bangkit.manduin.model.CommentModel
import com.bumptech.glide.Glide
import java.util.ArrayList

class ListCommentAdapter : RecyclerView.Adapter<ListCommentAdapter.ItemTarget>() {
    private val listReview = ArrayList<CommentModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listReview : List<CommentModel>) {
        this.listReview.clear()
        this.listReview.addAll(listReview)
        notifyDataSetChanged()
    }

    inner class ItemTarget(private val binding : ItemRowCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : CommentModel) {
            Glide.with(itemView.context)
                .load(model.image)
                .circleCrop()
                .into(binding.ivUser)
            binding.tvUser.text = model.name
            binding.tvComment.text = model.review
            binding.tvDate.text = model.date
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ItemTarget {
        return ItemTarget(
            ItemRowCommentBinding.inflate(
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