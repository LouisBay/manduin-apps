package com.bangkit.manduin.ui.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bangkit.manduin.model.OnBoardingItem
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.OnBoardingAdapter
import com.bangkit.manduin.databinding.ActivityOnboardingBinding
import com.bangkit.manduin.ui.auth.LoginActivity
import com.bangkit.manduin.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponent()
    }

    private val listOnBoard: ArrayList<OnBoardingItem>
        get() {
            val list = ArrayList<OnBoardingItem>()
            resources.apply {
                val title = getStringArray(R.array.title)
                val desc = getStringArray(R.array.description)
                val image = obtainTypedArray(R.array.image)

                for (i in title.indices) {
                    OnBoardingItem(
                        image.getResourceId(i, -1),
                        title[i],
                        desc[i],
                    ).also { list.add(it) }
                }
                image.recycle()
            }
            return list
        }

    private fun setupComponent() {
        onBoardingAdapter = OnBoardingAdapter(listOnBoard)

        setupPageIndicators()
        setupViewPager()

        binding.apply {
            fabNext.setOnClickListener(this@OnboardingActivity)
            tvSkip.setOnClickListener(this@OnboardingActivity)
        }
    }

    private fun setCurrentDots(index: Int) {
        val childCount = binding.dots.childCount
        for (i in 0 until childCount) {
            val imageView = binding.dots.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    if (index == i) R.drawable.onboarding_indicator_active
                    else R.drawable.onboarding_indicator_inactive
                )
            )
        }
    }

    private fun setupPageIndicators() {
        val indicators = arrayOfNulls<ImageView>(
            onBoardingAdapter.itemCount
        )

        val layout = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(8,0,8,0)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboarding_indicator_inactive
                    )
                )
                layoutParams = layout
                setOnClickListener {
                    binding.vpOnboard.currentItem = i
                }
            }
            binding.dots.addView(indicators[i])
        }
    }

    private fun setupViewPager() {
        binding.vpOnboard.apply {
            adapter = onBoardingAdapter

            setCurrentDots(0)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentDots(position)

                    binding.tvSkip.visibility = if (position == onBoardingAdapter.itemCount - 1) View.GONE else View.VISIBLE
                }
            })
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_next -> {
                with(binding) {
                    if (vpOnboard.currentItem + 1 < onBoardingAdapter.itemCount) {
                        vpOnboard.currentItem += 1
                    } else { toLoginPage() }
                }
            }

            R.id.tv_skip -> {
                toLoginPage()
                Log.d("TEST", "Terpencet ges")
            }
        }
    }

    private fun toLoginPage() {
        Intent(this@OnboardingActivity, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}