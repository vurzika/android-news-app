package com.onramp.vurzika.newsapp.ui.newslist

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

interface NewsListContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun showNews(newsArticles: List<NewsArticle>)

        fun showError(errorMessage: String?)

        fun showMessageAppOffline()

        fun showMessageEmptyFavorites()

        fun switchToHeadlinesSection()

        fun switchToFavoritesSection()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onHeadlinesSectionSelected()

        fun onFavoritesSectionSelected()

        fun onRefreshRequested()

    }
}