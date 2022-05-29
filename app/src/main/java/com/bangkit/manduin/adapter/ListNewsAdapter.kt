package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.R
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.databinding.ItemRowNewsBinding
import com.bumptech.glide.Glide

class ListNewsAdapter : RecyclerView.Adapter<ListNewsAdapter.ListViewHolder>() {
    private val listNews = ArrayList<NewsItem>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listNews: ArrayList<NewsItem>, tag: String?) {
        this.listNews.clear()
        this.listNews.addAll(
            if (tag == TAG_HOME) listNews.take(5) else listNews
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder {
        return ListViewHolder(
            ItemRowNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : ListViewHolder, position : Int) {
        val news = listNews[position]
        holder.bind(news)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(news) }
    }

    override fun getItemCount() = listNews.size

    class ListViewHolder(private val binding : ItemRowNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsItem) {
            binding.apply {
                tvTitleNews.text = news.title
                tvDescriptionNews.text = news.description

                Glide.with(ivNews.context)
                    .load(news.thumbnail)
                    .placeholder(R.drawable.image_preview)
                    .error(R.drawable.image_preview)
                    .centerCrop()
                    .into(ivNews)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: NewsItem)
    }

    companion object {
        const val TAG_HOME = "HOME"
    }
}