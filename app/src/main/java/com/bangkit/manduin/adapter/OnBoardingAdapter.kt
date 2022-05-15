package com.bangkit.manduin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.manduin.model.OnBoardingItem
import com.bangkit.manduin.databinding.OnboardingSlideBinding

class OnBoardingAdapter(private val onBoardingListItem: ArrayList<OnBoardingItem>) :
RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val binding = OnboardingSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingListItem[position])
    }

    override fun getItemCount() = onBoardingListItem.size

    class OnBoardingViewHolder(private val binding: OnboardingSlideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setOnBoardingData(onBoardingItem: OnBoardingItem) {
            binding.apply {
                tvTitleOnboard.text = onBoardingItem.title
                tvDescOnboard.text = onBoardingItem.description
                ivOnboard.setImageResource(onBoardingItem.image)
            }
        }
    }
}