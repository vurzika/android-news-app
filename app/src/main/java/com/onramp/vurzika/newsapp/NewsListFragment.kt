package com.onramp.vurzika.newsapp

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.onramp.vurzika.newsapp.databinding.FragmentNewsListBinding
import com.onramp.vurzika.newsapp.models.NewsArticle
import java.util.*

class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsArticlesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)

        adapter = NewsArticlesListAdapter(
                NewsArticleClickListener(
                        clickListener = {
                            findNavController().navigate(
                                    NewsListFragmentDirections.actionNewsListFragmentToDetailsFragment()
                            )
                        })
        )

        binding.newsList.adapter = adapter

        val divider = DividerItemDecoration(context, ClipDrawable.HORIZONTAL)
        binding.newsList.addItemDecoration(divider)

        // test data
        adapter.submitList(
                listOf(
                        NewsArticle("1", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("2", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("3", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("4", "ABC", "SITE ABC", Date(), null),
                        NewsArticle("5", "ABC", "SITE ABC", Date(), null),
                        NewsArticle("6", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("7", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("8", "ABC", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
                        NewsArticle("9", "ABC", "SITE ABC", Date(), null),
                        NewsArticle("10", "ABC", "SITE ABC", Date(), null),
                )
        )

        return binding.root
    }

}