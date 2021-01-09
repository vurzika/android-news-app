package com.onramp.vurzika.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.onramp.vurzika.newsapp.databinding.FragmentNewsDetailsBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.ArticleDetails
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding

    private lateinit var newsArticle: NewsArticle

    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        setHasOptionsMenu(true)

        // Test: Load Data
        SpaceFlightNewsApi.service.getArticle(args.newsArticleId).enqueue(object : Callback<ArticleDetails> {
            override fun onResponse(call: Call<ArticleDetails>, response: Response<ArticleDetails>) {
                val article = response.body()?.let {
                    NewsArticle(
                            id = args.newsArticleId,
                            title = it.title,
                            summary = it.summary ?: "Article Summary Not Available",
                            newsSite = it.newsSite ?: "Unknown Source",
                            publicationDate = it.publishedAt,
                            thumbnailUrl = it.imageUrl
                    )
                }

                newsArticle = article!!
                binding.newsArticle = newsArticle
            }

            override fun onFailure(call: Call<ArticleDetails>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    // UI Navigation Support

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // connect fragment's toolbar to navigation graph
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar
                .setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()

        // use fragment's toolbar as activity's main toolbar to populate menu
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
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