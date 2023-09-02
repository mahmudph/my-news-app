/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local

import androidx.room.RoomDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleRemoteKeyDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceRemoteKeyDao

abstract class AppDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): ArticleRemoteKeyDao

    abstract fun sourceDao(): SourceDao
    abstract fun sourceRemoteKeyDao(): SourceRemoteKeyDao
}