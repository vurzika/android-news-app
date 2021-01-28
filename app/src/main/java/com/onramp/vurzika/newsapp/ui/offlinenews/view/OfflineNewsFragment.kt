package com.onramp.vurzika.newsapp.ui.offlinenews.view

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentOfflineNewsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.NavigationToolbarObserver
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseFragment
import com.onramp.vurzika.newsapp.ui.latestnews.view.NewsArticleClickListener
import com.onramp.vurzika.newsapp.ui.latestnews.view.NewsArticlesListAdapter
import com.onramp.vurzika.newsapp.ui.offlinenews.OfflineNewsContract
import com.onramp.vurzika.newsapp.ui.offlinenews.presenter.OfflineNewsPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OfflineNewsFragment : BaseFragment<OfflineNewsContract.View>(), OfflineNewsContract.View {

    private lateinit var binding: FragmentOfflineNewsBinding

    @Inject
    lateinit var adapter: NewsArticlesListAdapter

    @Inject
    lateinit var presenter: OfflineNewsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline_news, container, false)

        setHasOptionsMenu(true)

        adapter.onItemClickListener = NewsArticleClickListener { itemId ->
            findNavController().navigate(
                    OfflineNewsFragmentDirections.actionOfflineNewsFragmentToNewsDetailsFragment(itemId)
            )
        }

        binding.newsList.adapter = adapter

        // Connect Fragment specific toolbar to navigation controller
        lifecycle.addObserver(
                NavigationToolbarObserver(requireActivity() as AppCompatActivity, binding.toolbar))

        initSwipeToDelete()

        return binding.root
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
            ): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                    recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean = false

            // on swipe notify presenter to apply changes in model
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as NewsArticlesListAdapter.ViewHolder).newsArticleId.let {
                    presenter.onNewsArticleRemovedFromOfflineList(it)
                }
            }
        }).attachToRecyclerView(binding.newsList)
    }

    // View

    private fun hideAllUiElements() {
        binding.newsList.visibility = View.GONE
        binding.viewErrorMessage.visibility = View.GONE
        binding.viewNoFavorites.visibility = View.GONE
    }

    override fun getPresenter(): BaseContract.Presenter<OfflineNewsContract.View> {
        return presenter
    }

    override fun showNews(newsArticles: List<NewsArticle>) {
        hideAllUiElements()

        binding.newsList.visibility = View.VISIBLE

        adapter.submitList(newsArticles)
        adapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String?) {
        hideAllUiElements()

        binding.viewErrorMessage.visibility = View.VISIBLE
    }

    override fun showMessageEmptyFavorites() {
        hideAllUiElements()

        binding.viewNoFavorites.visibility = View.VISIBLE
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
                findNavController().navigate(OfflineNewsFragmentDirections.actionOfflineNewsFragmentToSettingsActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}