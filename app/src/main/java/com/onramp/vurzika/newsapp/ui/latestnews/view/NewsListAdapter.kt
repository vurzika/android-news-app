package com.onramp.vurzika.newsapp.ui.latestnews.view

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.databinding.NewsListItemBinding
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsArticlesListAdapter @Inject constructor() :
        ListAdapter<NewsArticle, NewsArticlesListAdapter.ViewHolder>(NewsArticleDiffCallback()) {

    @Inject
    lateinit var userSettings: SharedPreferences

    @Inject
    @ApplicationContext
    lateinit var context: Context

    lateinit var onItemClickListener: NewsArticleClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)

        val isThumbnailEnabled =
                userSettings.getBoolean(context.getString(R.string.setting_key_show_thumbnails), true)

        viewHolder.bind(item, isThumbnailEnabled, onItemClickListener)
    }

    class ViewHolder(private val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var newsArticleId: String = ""

        fun bind(item: NewsArticle, isThumbnailEnabled: Boolean, clickListener: NewsArticleClickListener) {
            newsArticleId = item.id
            binding.isThumbnailEnabled = isThumbnailEnabled
            binding.newsArticle = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NewsArticleClickListener(
        val clickListener: (newsArticleId: String) -> Unit,
) {
    fun onClick(newsArticle: NewsArticle) = clickListener(newsArticle.id)
}


// Implementing NewsArticleDiffCallback to figure out how the list and items have changed
class NewsArticleDiffCallback : DiffUtil.ItemCallback<NewsArticle>() {

    // Check whether the two passed-in NewsArticle items, oldItem and newItem, are the same
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.id == newItem.id
    }

    // Check whether oldItem and newItem contain the same data; that is, whether they are equal
    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem == newItem
    }
}
