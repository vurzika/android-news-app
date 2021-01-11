package com.onramp.vurzika.newsapp.ui.newslist.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class NewsListPresenter @Inject constructor() : BasePresenter<NewsListContract.View>(), NewsListContract.Presenter {

    enum class SelectedSection {
        NEWS, FAVORITES
    }

    @Inject
    lateinit var newsRepository: NewsRepository

    private var selectedSection = SelectedSection.NEWS

    override fun onViewCreated() {
        refreshSelectedSection()
    }

    override fun onHeadlinesSectionSelected() {
        view?.switchToHeadlinesSection()

        selectedSection = SelectedSection.NEWS

        launch {
            view?.showLoading()

            try {
                view?.showNews(newsRepository.getNewsArticles())
            } catch (error: IOException) {
                view?.showMessageAppOffline()
            } catch (error: Exception) {
                view?.showError(error.message)
            }
        }
    }

    override fun onFavoritesSectionSelected() {
        view?.switchToFavoritesSection()

        selectedSection = SelectedSection.FAVORITES

        launch {
            view?.showLoading()

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

    override fun onRefreshRequested() {
        refreshSelectedSection()
    }

    private fun refreshSelectedSection() {
        when (selectedSection) {
            SelectedSection.NEWS -> onHeadlinesSectionSelected()
            SelectedSection.FAVORITES -> onFavoritesSectionSelected()
        }
    }
}