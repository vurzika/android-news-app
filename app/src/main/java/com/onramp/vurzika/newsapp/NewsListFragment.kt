package com.onramp.vurzika.newsapp

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
import com.onramp.vurzika.newsapp.databinding.FragmentNewsListBinding
import com.onramp.vurzika.newsapp.utils.DemoDataUtils

class NewsListFragment : Fragment() {

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

        adapter.submitList(
                DemoDataUtils.getLatestNews()
        )

        return binding.root
    }

    // Navigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // connect fragment's toolbar to navigation graph
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar
                .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()

        // Navigation: use fragment's toolbar as activity's main toolbar to populate menu
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
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