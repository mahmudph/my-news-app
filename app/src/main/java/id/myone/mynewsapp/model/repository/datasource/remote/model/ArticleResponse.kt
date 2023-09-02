/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class ArticleResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
)