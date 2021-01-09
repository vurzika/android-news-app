package com.onramp.vurzika.newsapp.ui.newsdetails

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

class NewsDetailsContract {

    interface View : BaseContract.View {

        fun showLoadingIndicator(visible: Boolean)

        fun showArticle(newsArticle: NewsArticle)

        fun showError(errorMessage: String)
    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}