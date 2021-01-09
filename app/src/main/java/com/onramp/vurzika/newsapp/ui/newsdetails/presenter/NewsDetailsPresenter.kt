package com.onramp.vurzika.newsapp.ui.newsdetails.presenter

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.ArticleDetails
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApi
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newsdetails.NewsDetailsContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsPresenter(private val newsArticleId: String) : BasePresenter<NewsDetailsContract.View>(), NewsDetailsContract.Presenter {

    private lateinit var newsArticle: NewsArticle

    override fun onViewCreated() {
        view?.showLoadingIndicator(true)

        SpaceFlightNewsApi.service.getArticle(newsArticleId).enqueue(object : Callback<ArticleDetails> {
            override fun onResponse(call: Call<ArticleDetails>, response: Response<ArticleDetails>) {
                view?.showLoadingIndicator(false)

                val article = response.body()?.let {
                    NewsArticle(
                            id = newsArticleId,
                            title = it.title,
                            summary = it.summary ?: "Article Summary Not Available",
                            newsSite = it.newsSite ?: "Unknown Source",
                            publicationDate = it.publishedAt,
                            thumbnailUrl = it.imageUrl
                    )
                }

                newsArticle = article!!

                view?.showArticle(newsArticle)
            }

            override fun onFailure(call: Call<ArticleDetails>, t: Throwable) {
                view?.showLoadingIndicator(false)

                view?.showError(t.message ?: "Unknown error")
            }
        })
    }
}