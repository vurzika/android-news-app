package com.onramp.vurzika.newsapp.ui.newsdetails.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.net.toUri
import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsPresenter @Inject constructor(
        @ApplicationContext private val context: Context,
        private val newsRepository: NewsRepository
) : BasePresenter<NewsDetailsContract.View>(), NewsDetailsContract.Presenter {

    private lateinit var newsArticle: NewsArticle

    private val clipboardManager: ClipboardManager by lazy {
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    lateinit var newsArticleId: String

    override fun onViewCreated() {
        launch {
            view?.showLoadingIndicator()

            try {
                newsArticle = newsRepository.getNewsArticle(newsArticleId)

                view?.showArticle(newsArticle)
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            }
        }
    }

    override fun onSetFavoriteSelected() {
        launch {
            try {
                if (newsArticle.isStored) {
                    newsRepository.deleteNewsArticle(newsArticleId)
                } else {
                    newsRepository.saveNewsArticle(newsArticle)
                }

                newsArticle.isStored = !newsArticle.isStored

                view?.showArticle(newsArticle)
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            }
        }
    }

    override fun onLinkCopyActionSelected() {

        newsArticle.url?.let {

            clipboardManager.setPrimaryClip(ClipData.newUri(
                    context.contentResolver,
                    newsArticle.summary,
                    it.toUri())
            )

            view?.notifyLinkCopiedToClipboard()
        }
    }

    override fun onOpenWithActionSelected() {

        newsArticle.url?.let {
            view?.openLinkWithExternalBrowser(it.toUri())
        }
    }
}