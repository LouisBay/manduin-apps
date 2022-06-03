package com.bangkit.manduin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.manduin.R
import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bumptech.glide.Glide

class LandmarkSliderAdapter(private val listSliderItemDestination: ArrayList<LandmarkItem>, private val viewPager: ViewPager2) :
    RecyclerView.Adapter<LandmarkSliderAdapter.SliderViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_destination)
        val place: TextView = itemView.findViewById(R.id.place_slider)
        val location: TextView = itemView.findViewById(R.id.location_slider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val listDestination = listSliderItemDestination[position]

        Glide.with(holder.itemView.context)
            .load(listDestination.imgUrl)
            .into(holder.image)

        holder.place.text = listDestination.nama
        holder.location.text = listDestination.city

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDestination) }

        if (position == listSliderItemDestination.size-1){
            viewPager.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return listSliderItemDestination.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        listSliderItemDestination.addAll(listSliderItemDestination)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(landmarkItem: LandmarkItem)
    }
}