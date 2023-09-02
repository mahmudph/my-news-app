/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.extensions

import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.remote.model.Article


fun Article.mapToEntity (): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}