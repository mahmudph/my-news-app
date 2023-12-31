/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.remote

import id.myone.mynewsapp.model.repository.datasource.remote.model.ArticleResponse
import id.myone.mynewsapp.model.repository.datasource.remote.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {
    @GET("/v2/top-headlines")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("sources") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("language") language: String,
    ): ArticleResponse
    @GET("/v2/sources")
    suspend fun getSources(
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): SourceResponse
}