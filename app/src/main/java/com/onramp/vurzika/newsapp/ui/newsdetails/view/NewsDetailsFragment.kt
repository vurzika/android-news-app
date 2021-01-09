package com.onramp.vurzika.newsapp.ui.newsdetails.view

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
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.FragmentNewsDetailsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import com.onramp.vurzika.newsapp.ui.newsdetails.presenter.NewsDetailsPresenter

class NewsDetailsFragment : Fragment(), NewsDetailsContract.View {

    private lateinit var binding: FragmentNewsDetailsBinding

    private val args: NewsDetailsFragmentArgs by navArgs()

    private lateinit var presenter: NewsDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        //presenter = NewsDetailsPresenter(args.newsArticleId)

        setHasOptionsMenu(true)

        setupNavigationComponent()

        return binding.root
    }

    // UI Navigation Support

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

        presenter = NewsDetailsPresenter(args.newsArticleId)
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
        // TODO("Not yet implemented")
    }

    override fun showArticle(newsArticle: NewsArticle) {
        binding.newsArticle = newsArticle
    }

    override fun showError(errorMessage: String) {
        // TODO("Not yet implemented")
    }

    // Options Menu

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
}