package com.onramp.vurzika.newsapp.ui.newsdetails

import android.net.Uri
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.ui.base.mvp.BaseContract

class NewsDetailsContract {

    interface View : BaseContract.View {

        fun showLoadingIndicator(visible: Boolean)

        fun showArticle(newsArticle: NewsArticle)

        fun showError(errorMessage: String)

        fun notifyLinkCopiedToClipboard()

        fun openLinkWithExternalBrowser(pageUri: Uri)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onSetFavoriteSelected()

        fun onLinkCopyActionSelected()

        fun onOpenWithActionSelected()
    }
}