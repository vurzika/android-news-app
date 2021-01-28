package com.onramp.vurzika.newsapp.ui.latestnews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentLatestNewsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.NavigationToolbarObserver
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseFragment
import com.onramp.vurzika.newsapp.ui.latestnews.LatestNewsContract
import com.onramp.vurzika.newsapp.ui.latestnews.presenter.LatestNewsPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LatestNewsFragment : BaseFragment<LatestNewsContract.View>(), LatestNewsContract.View {

    private lateinit var binding: FragmentLatestNewsBinding

    @Inject
    lateinit var adapter: NewsArticlesListAdapter

    @Inject
    lateinit var presenter: LatestNewsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_latest_news, container, false)

        setHasOptionsMenu(true)

        adapter.onItemClickListener = NewsArticleClickListener { itemId ->
            findNavController().navigate(
                    LatestNewsFragmentDirections.actionLatestNewsFragmentToDetailsFragment(itemId)
            )
        }

        binding.newsList.adapter = adapter

        binding.newsSwipeRefreshLayout.setOnRefreshListener {
            presenter.onRefreshRequested()
        }

        // Connect Fragment specific toolbar to navigation controller
        lifecycle.addObserver(
                NavigationToolbarObserver(requireActivity() as AppCompatActivity, binding.toolbar))

        return binding.root
    }

    // View

    private fun hideAllUiElements() {
        binding.newsList.visibility = View.GONE
        binding.viewErrorMessage.visibility = View.GONE
        binding.viewOfflineMode.visibility = View.GONE
        binding.newsSwipeRefreshLayout.isRefreshing = false
    }

    override fun getPresenter(): BaseContract.Presenter<LatestNewsContract.View> {
        return presenter
    }

    override fun showLoading() {
        hideAllUiElements()

        binding.newsSwipeRefreshLayout.visibility = View.VISIBLE

        // force SwipeRefreshLayout displaying loading indicator
        binding.newsSwipeRefreshLayout.post {
            binding.newsSwipeRefreshLayout.isRefreshing = true
        }
    }

    override fun showNews(newsArticles: List<NewsArticle>) {
        hideAllUiElements()

        adapter.submitList(newsArticles)
        adapter.notifyDataSetChanged()

        binding.newsList.visibility = View.VISIBLE
    }

    override fun showError(errorMessage: String?) {
        hideAllUiElements()

        binding.viewErrorMessage.visibility = View.VISIBLE
    }

    override fun showMessageAppOffline() {
        hideAllUiElements()

        binding.viewOfflineMode.visibility = View.VISIBLE
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
                findNavController().navigate(LatestNewsFragmentDirections.actionLatestNewsFragmentToSettingsActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}