package com.onramp.vurzika.newsapp.ui.latestnews.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.latestnews.LatestNewsContract
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class LatestNewsPresenter @Inject constructor(
        private val newsRepository: NewsRepository
) : BasePresenter<LatestNewsContract.View>(), LatestNewsContract.Presenter {

    override fun onViewCreated() {
        refreshNews()
    }

    override fun onRefreshRequested() {
        refreshNews()
    }

    private fun refreshNews() {
        launch {
            view?.showLoading()

            try {
                val newsList = newsRepository.getNewsArticles()

                saveLatestNewsUpdateDate(newsList)

                view?.showNews(newsList)
            } catch (error: IOException) {
                view?.showMessageAppOffline()
            } catch (error: Exception) {
                view?.showError(error.message)
            }
        }
    }

    private fun saveLatestNewsUpdateDate(newsList: List<NewsArticle>) {
        val latestNews = newsList.maxByOrNull { it.publicationDate }
        latestNews?.let {
            newsRepository.setLastRetrievedNewsDate(it.publicationDate)
        }
    }
}