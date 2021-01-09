package com.onramp.vurzika.newsapp.ui.newslist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentNewsListBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import com.onramp.vurzika.newsapp.ui.newslist.presenter.NewsListPresenter

class NewsListFragment : Fragment(), NewsListContract.View {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsArticlesListAdapter

    private var presenter = NewsListPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)

        setHasOptionsMenu(true)

        setupNavigationComponent()

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

    private fun setupNavigationComponent() {
        // connect fragment's toolbar to navigation graph
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar
                .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()

        setCurrentToolbarAsMain()
    }

    private fun setCurrentToolbarAsMain() {
        // Navigation: use fragment's toolbar as activity's main toolbar to populate menu
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    // View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.onAttach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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
}