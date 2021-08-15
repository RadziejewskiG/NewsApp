package com.radziejewskig.data.framework.retrofit

import com.radziejewskig.data.model.api.NewsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("articles")
    suspend fun getArticlesPaged(
        @Query("page") page: Int
    ) : Response<List<NewsApiModel>>

    @GET("articles/{articleId}")
    suspend fun getArticleDetails(
        @Path("articleId") articleId: String
    ) : Response<NewsApiModel>

}
