package com.onramp.vurzika.newsapp.ui.newslist.presenter

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.ArticleSummary
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApi
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.newslist.NewsListContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListPresenter : BasePresenter<NewsListContract.View>(), NewsListContract.Presenter {

    override fun onViewCreated() {
        loadNews()
    }

    override fun loadNews() {
        view?.showLoadingIndicator(true)

        SpaceFlightNewsApi.service.getNewsArticles().enqueue(object : Callback<List<ArticleSummary>> {
            override fun onResponse(call: Call<List<ArticleSummary>>, response: Response<List<ArticleSummary>>) {
                view?.showLoadingIndicator(false)

                val newsArticles = response.body()?.map {
                    NewsArticle(
                            id = it.id,
                            title = it.title,
                            summary = it.summary ?: "Article Summary Not Available",
                            newsSite = it.newsSite ?: "Unknown Source",
                            publicationDate = it.publishedAt,
                            thumbnailUrl = it.imageUrl
                    )
                }

                newsArticles?.let { view?.showNews(newsArticles) }
            }

            override fun onFailure(call: Call<List<ArticleSummary>>, t: Throwable) {
                view?.showLoadingIndicator(false)

                view?.showError(t.message ?: "Unknown error")
            }
        })
    }
}