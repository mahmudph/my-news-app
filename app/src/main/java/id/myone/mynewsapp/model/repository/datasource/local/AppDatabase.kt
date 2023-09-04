/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleRemoteKeyDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceRemoteKeyDao
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleRemoteKeyEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceRemoteKeyEntity


@Database(version = 1, entities = [
    SourceEntity::class,
    ArticleEntity::class,
    SourceRemoteKeyEntity::class,
    ArticleRemoteKeyEntity::class,
], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): ArticleRemoteKeyDao

    abstract fun sourceDao(): SourceDao
    abstract fun sourceRemoteKeyDao(): SourceRemoteKeyDao
}