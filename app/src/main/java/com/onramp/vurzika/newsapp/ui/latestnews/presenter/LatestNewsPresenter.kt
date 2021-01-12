package com.onramp.vurzika.newsapp.ui.latestnews.presenter

import com.onramp.vurzika.newsapp.repository.NewsRepository
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.latestnews.LatestNewsContract
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class LatestNewsPresenter @Inject constructor() : BasePresenter<LatestNewsContract.View>(), LatestNewsContract.Presenter {

    @Inject
    lateinit var newsRepository: NewsRepository

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
                view?.showNews(newsRepository.getNewsArticles())
            } catch (error: IOException) {
                view?.showMessageAppOffline()
            } catch (error: Exception) {
                view?.showError(error.message)
            }
        }
    }
}