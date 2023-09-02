package id.myone.mynewsapp.model.repository.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseEntity
import id.myone.mynewsapp.model.repository.datasource.remote.model.Source

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,

    @ColumnInfo(name = "publish_at")
    val publishedAt: String?,

    @Embedded(prefix = "arc_")
    val source: Source?,
    val title: String?,
    val url: String?,

    @ColumnInfo(name="url_to_image")
    val urlToImage: String?,
): BaseEntity
