package com.bangkit.manduin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

class SliderAdapter(private val listSliderItemDestination: ArrayList<SliderItemDestination>, private val viewPager: ViewPager2) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

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
        val listDestionation = listSliderItemDestination[position]
        Glide.with(holder.itemView.context)
            .load(listDestionation.image)
            .into(holder.image)
        holder.place.text = listDestionation.place
        holder.location.text = listDestionation.location
        if (position == listSliderItemDestination.size-1){
            viewPager.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return listSliderItemDestination.size
    }

    private val runnable = Runnable {
        listSliderItemDestination.addAll(listSliderItemDestination)
        notifyDataSetChanged()
    }
}