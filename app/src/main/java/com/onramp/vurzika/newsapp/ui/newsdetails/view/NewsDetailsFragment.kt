package com.onramp.vurzika.newsapp.ui.newsdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentNewsDetailsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.BaseNavigationFragment
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import com.onramp.vurzika.newsapp.ui.newsdetails.presenter.NewsDetailsPresenter

class NewsDetailsFragment : BaseNavigationFragment<NewsDetailsContract.View>(), NewsDetailsContract.View {

    private lateinit var binding: FragmentNewsDetailsBinding

    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    // MVP

    override fun createPresenter(): BaseContract.Presenter<NewsDetailsContract.View> {
        return NewsDetailsPresenter(args.newsArticleId)
    }

    override fun showLoadingIndicator(visible: Boolean) {
        // TODO("Not yet implemented")
    }

    override fun showArticle(newsArticle: NewsArticle) {
        binding.newsArticle = newsArticle
    }

    override fun showError(errorMessage: String) {
        // TODO("Not yet implemented")
    }

    // Options Menu Configuration

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_news_details, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_copy_link -> true
            R.id.action_open_with -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Navigation UI Configuration

    override fun getToolbar(): Toolbar {
        return binding.toolbar
    }
}