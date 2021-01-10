package com.onramp.vurzika.newsapp.ui.newsdetails.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import kotlinx.coroutines.launch

class NewsDetailsPresenter(private val newsArticleId: String) : BasePresenter<NewsDetailsContract.View>(), NewsDetailsContract.Presenter {

    private lateinit var newsArticle: NewsArticle

    override fun onViewCreated() {
        launch {
            view?.showLoadingIndicator(true)

            try {
                newsArticle = NewsRepository.getNewsArticle(newsArticleId)

                view?.showArticle(newsArticle)
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            } finally {
                view?.showLoadingIndicator(false)
            }
        }
    }
}