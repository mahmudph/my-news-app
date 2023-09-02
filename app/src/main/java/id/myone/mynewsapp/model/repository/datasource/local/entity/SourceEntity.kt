package id.myone.mynewsapp.model.repository.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.mynewsapp.common.sourceTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseEntity


@Entity(tableName = sourceTable)
data class SourceEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: String,
    val category: String,
    val country: String,
    val description: String,
    val language: String,
    val name: String,
    val url: String,
): BaseEntity
