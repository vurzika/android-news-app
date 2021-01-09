package com.onramp.vurzika.newsapp.ui.newslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentNewsListBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.BaseNavigationFragment
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import com.onramp.vurzika.newsapp.ui.newslist.presenter.NewsListPresenter

class NewsListFragment : BaseNavigationFragment<NewsListContract.View>(), NewsListContract.View {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsArticlesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)

        setHasOptionsMenu(true)

        adapter = NewsArticlesListAdapter(
                NewsArticleClickListener(
                        clickListener = { itemId ->
                            findNavController().navigate(
                                    NewsListFragmentDirections.actionNewsListFragmentToDetailsFragment(itemId)
                            )
                        })
        )

        binding.newsList.adapter = adapter

        return binding.root
    }

    // MVP

    override fun createPresenter(): BaseContract.Presenter<NewsListContract.View> {
        return NewsListPresenter()
    }

    override fun showLoadingIndicator(visible: Boolean) {
        binding.emptyView.visibility = View.GONE
        binding.newsSwipeRefreshLayout.visibility = View.VISIBLE

        // force SwipeRefreshLayout displaying loading indicator
        binding.newsSwipeRefreshLayout.post {
            binding.newsSwipeRefreshLayout.isRefreshing = visible
        }
    }

    override fun showNews(newsArticles: List<NewsArticle>) {
        binding.emptyView.visibility = View.GONE
        binding.newsSwipeRefreshLayout.visibility = View.VISIBLE

        adapter.submitList(newsArticles)
    }

    override fun showError(errorMessage: String) {
        binding.newsSwipeRefreshLayout.visibility = View.GONE
        binding.emptyView.visibility = View.VISIBLE

        binding.emptyView.text = errorMessage
    }

    // Options Menu

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {
                // handle navigation manually as we are navigating to different activity
                findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToSettingsActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Navigation UI configuration

    override fun getToolbar(): Toolbar {
        return binding.toolbar
    }
}