package com.onramp.vurzika.newsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onramp.vurzika.newsapp.databinding.NewsListItemBinding
import com.onramp.vurzika.newsapp.models.NewsArticle
import com.squareup.picasso.Picasso

class NewsArticlesListAdapter(private val clickListener: NewsArticleClickListener) :
        ListAdapter<NewsArticle, NewsArticlesListAdapter.ViewHolder>(NewsArticleDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item, clickListener)
    }

    class ViewHolder(private val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var newsArticleId: String = ""

        fun bind(item: NewsArticle, clickListener: NewsArticleClickListener) {
            newsArticleId = item.id
            binding.newsArticle = item
            binding.clickListener = clickListener

            // download thumbnail if exists
            // TODO: move to data-binding layer
            item.thumbnailUrl?.let {
                Picasso.get()
                        .load(item.thumbnailUrl)
                        .resize(80, 120)
                        .centerInside()
                        .into(binding.newsThumbnail)
            }
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
