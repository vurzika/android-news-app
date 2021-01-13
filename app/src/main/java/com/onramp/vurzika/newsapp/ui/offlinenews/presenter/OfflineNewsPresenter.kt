package com.onramp.vurzika.newsapp.ui.offlinenews.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.offlinenews.OfflineNewsContract
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineNewsPresenter @Inject constructor() : BasePresenter<OfflineNewsContract.View>(), OfflineNewsContract.Presenter {

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onViewCreated() {
        refreshOfflineNews()
    }

    private fun refreshOfflineNews() {
        launch {
            try {
                val newsArticles = newsRepository.getSavedNewsArticles()

                if (newsArticles.isNotEmpty()) {
                    view?.showNews(newsArticles)
                } else {
                    view?.showMessageEmptyFavorites()
                }
            } catch (error: Exception) {
                view?.showError(error.message)
            }
        }
    }

    override fun onNewsArticleRemovedFromOfflineList(newsArticleId: String) {
        launch {
            try {
                newsRepository.deleteNewsArticle(newsArticleId)

                refreshOfflineNews()
            } catch (error: Error) {
                view?.showError(error.message)
            }
        }
    }
}