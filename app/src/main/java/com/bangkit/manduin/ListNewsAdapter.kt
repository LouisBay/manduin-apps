package com.bangkit.manduin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.databinding.ItemRowNewsBinding
import com.bumptech.glide.Glide
import java.util.ArrayList

class ListNewsAdapter() : RecyclerView.Adapter<ListNewsAdapter.ItemTarget>() {
    private val listNews = ArrayList<NewsEntity>()

    fun setList(listNews : List<NewsEntity>) {
        this.listNews.clear()
        this.listNews.addAll(listNews)
        notifyDataSetChanged()
    }

    inner class ItemTarget(private val binding : ItemRowNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : NewsEntity) {
            Glide.with(itemView.context)
                .load(model.news_image)
                .centerCrop()
                .into(binding.ivNews)
            binding.tvTitleNews.text = model.news_title
            binding.tvDescriptionNews.text = model.news_description
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : ItemTarget {
        return ItemTarget(
            ItemRowNewsBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ItemTarget , position : Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount() : Int = listNews.size
}