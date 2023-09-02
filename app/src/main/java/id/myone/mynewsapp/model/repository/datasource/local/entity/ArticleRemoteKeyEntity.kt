package id.myone.mynewsapp.model.repository.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.mynewsapp.common.articleRemoteKeyTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseRemoteKeyEntity


@Entity(tableName = articleRemoteKeyTable)
data class ArticleRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val prePage: Int?,
    override val nexPage: Int?,
): BaseRemoteKeyEntity
