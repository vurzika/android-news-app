package com.onramp.vurzika.newsapp.ui.latestnews

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

interface LatestNewsContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun showNews(newsArticles: List<NewsArticle>)

        fun showError(errorMessage: String?)

        fun showMessageAppOffline()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onRefreshRequested()

    }
}