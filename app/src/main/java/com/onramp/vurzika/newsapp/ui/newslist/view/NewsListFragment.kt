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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : BaseNavigationFragment<NewsListContract.View>(), NewsListContract.View {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsArticlesListAdapter

    @Inject
    lateinit var presenter: NewsListPresenter

    private var shouldHandleBottomNavigationEvents = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

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

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            if (!shouldHandleBottomNavigationEvents) {
                return@setOnNavigationItemSelectedListener false
            }

            when (item.itemId) {
                R.id.headlines -> {
                    presenter.onHeadlinesSectionSelected()
                    true
                }
                R.id.favorites -> {
                    presenter.onFavoritesSectionSelected()
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

        binding.newsList.adapter = adapter

        return binding.root
    }

    // View

    override fun getPresenter(): BaseContract.Presenter<NewsListContract.View> {
        return presenter
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

    override fun switchToHeadlinesSection() {
        shouldHandleBottomNavigationEvents = false
        binding.bottomNavigation.selectedItemId = R.id.headlines
        shouldHandleBottomNavigationEvents = true

    }

    override fun switchToFavoritesSection() {
        shouldHandleBottomNavigationEvents = false
        binding.bottomNavigation.selectedItemId = R.id.favorites
        shouldHandleBottomNavigationEvents = true
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