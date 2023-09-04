package id.myone.mynewsapp.model.repository.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.mynewsapp.common.sourceRemoteKeyTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseRemoteKeyEntity

@Entity(tableName = sourceRemoteKeyTable)
data class SourceRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,

    override val prePage: Int?,
    override val nexPage: Int?,
): BaseRemoteKeyEntity