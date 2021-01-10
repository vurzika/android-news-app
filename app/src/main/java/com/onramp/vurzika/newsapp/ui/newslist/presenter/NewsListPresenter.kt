package com.onramp.vurzika.newsapp.ui.newslist.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListPresenter @Inject constructor() : BasePresenter<NewsListContract.View>(), NewsListContract.Presenter {

    @Inject
    lateinit var newsRepository: NewsRepository

    private var shouldShowLiveNews = true

    override fun onViewCreated() {
        view?.switchToHeadlinesSection()

        if (shouldShowLiveNews) {
            onHeadlinesSectionSelected()
        } else {
            onFavoritesSectionSelected()
        }
    }

    override fun onHeadlinesSectionSelected() {
        view?.switchToHeadlinesSection()

        shouldShowLiveNews = true

        loadNews()
    }

    override fun onFavoritesSectionSelected() {
        view?.switchToFavoritesSection()

        shouldShowLiveNews = false

        loadNews()
    }

    private fun loadNews() {
        launch {
            view?.showLoadingIndicator(true)

            try {
                val newsArticles = if (shouldShowLiveNews) {
                    newsRepository.getNewsArticles()
                } else {
                    newsRepository.getSavedNewsArticles()
                }

                view?.showNews(newsArticles)
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            } finally {
                view?.showLoadingIndicator(false)
            }
        }
    }
}