package com.onramp.vurzika.newsapp.ui.newslist.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import kotlinx.coroutines.launch

class NewsListPresenter : BasePresenter<NewsListContract.View>(), NewsListContract.Presenter {

    override fun onViewCreated() {
        loadNews()
    }

    override fun loadNews() {
        launch {
            view?.showLoadingIndicator(true)

            try {
                view?.showNews(NewsRepository.getNewsArticles())
            } catch (error: Exception) {
                view?.showError(error.message ?: "Unknown error")
            } finally {
                view?.showLoadingIndicator(false)
            }
        }
    }
}