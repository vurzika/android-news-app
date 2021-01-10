package com.onramp.vurzika.newsapp.ui.newslist

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

interface NewsListContract {

    interface View : BaseContract.View {

        fun showLoadingIndicator(visible: Boolean)

        fun showNews(newsArticles: List<NewsArticle>)

        fun showError(errorMessage: String)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun loadNews()

    }
}