package com.onramp.vurzika.newsapp.ui.offlinenews

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

interface OfflineNewsContract {

    interface View : BaseContract.View {

        fun showNews(newsArticles: List<NewsArticle>)

        fun showError(errorMessage: String?)

        fun showMessageEmptyFavorites()
    }

    interface Presenter : BaseContract.Presenter<View>
}