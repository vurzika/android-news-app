package com.onramp.vurzika.newsapp.ui.newsdetails.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsPresenter @Inject constructor() : BasePresenter<NewsDetailsContract.View>(), NewsDetailsContract.Presenter {

    private lateinit var newsArticle: NewsArticle

    lateinit var newsArticleId: String

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onViewCreated() {
        launch {
            view?.showLoadingIndicator(true)

            try {
                newsArticle = newsRepository.getNewsArticle(newsArticleId)

                view?.showArticle(newsArticle)
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            } finally {
                view?.showLoadingIndicator(false)
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
}