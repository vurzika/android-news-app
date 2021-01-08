package com.onramp.vurzika.newsapp.utils

import com.onramp.vurzika.newsapp.models.NewsArticle
import java.util.Date

object DemoDataUtils {

    private val demoData = listOf(
            NewsArticle("1", "SpaceX’s next Falcon Heavy booster arrives in Texas for static fire", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("2", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("3", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("4", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), null),
            NewsArticle("5", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), null),
            NewsArticle("6", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("7", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("8", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), "https://www.teslarati.com/wp-content/uploads/2019/04/Falcon-Heavy-center-core-B1057-static-fire-test-McGregor-042619-SpaceX-1-edit-c.jpg"),
            NewsArticle("9", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), null),
            NewsArticle("10", "ABC", "SpaceX’s fourth Falcon Heavy launch continues to inch closer, most recently celebrating what appears to be the arrival of one of three...", "SITE ABC", Date(), null),
    )

    fun getLatestNews() = demoData

    fun getNewsDetails(id: String) = demoData.find { it.id == id }

}