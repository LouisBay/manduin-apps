package com.bangkit.manduin.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.manduin.R
import com.bangkit.manduin.adapter.ListNewsAdapter
import com.bangkit.manduin.data.remote.response.NewsItem
import com.bangkit.manduin.databinding.ActivityAllNewsBinding
import com.bangkit.manduin.utils.Result
import com.bangkit.manduin.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllNewsBinding
    private lateinit var newsAdapter: ListNewsAdapter

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.tvTitle.text = resources.getString(R.string.travel_news)
        setNewsRecyclerView()

        binding.shimmerNews.startShimmer()
        binding.actionBar.btnBack.setOnClickListener { finish() }
    }

    private fun setNewsRecyclerView() {
        newsAdapter = ListNewsAdapter()

        binding.rvAllNews.apply {
            layoutManager = LinearLayoutManager(this@AllNewsActivity)
            adapter = newsAdapter
        }

        newsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(news: NewsItem) {
                if (news.link == null) {
                    Toast.makeText(this@AllNewsActivity, resources.getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                } else {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(news.link.toString())
                    }.also { startActivity(it) }
                }
            }
        })

        loadNews()
    }

    private fun loadNews() {
        newsViewModel.getTravelNewsData().observe(this) { result ->
            showResult(result)
        }
    }

    private fun showResult(result: Result<ArrayList<NewsItem>>?) {
        when (result) {
            is Result.Loading -> { setShimmer(true) }
            is Result.Success -> {
                lifecycleScope.launch {
                    delay(2000L)
                    newsAdapter.setList(result.data, null)
                    setShimmer(false)
                }
            }
            is Result.Error -> {
                Toast.makeText(this, resources.getString(R.string.error_occured, result.errorMessage), Toast.LENGTH_SHORT).show()
                setShimmer(false)
            }
            else -> {}
        }
    }

    private fun setShimmer(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerNews.visibility = View.VISIBLE
                shimmerNews.startShimmer()
                rvAllNews.visibility = View.GONE
            } else {
                shimmerNews.visibility = View.GONE
                shimmerNews.stopShimmer()
                rvAllNews.visibility = View.VISIBLE
            }
        }
    }
}