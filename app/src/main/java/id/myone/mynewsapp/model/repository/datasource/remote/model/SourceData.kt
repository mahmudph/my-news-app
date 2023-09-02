package id.myone.mynewsapp.model.repository.datasource.remote.model

import androidx.annotation.Keep

@Keep
data class SourceData(
    val category: String,
    val country: String,
    val description: String,
    val id: String,
    val language: String,
    val name: String,
    val url: String
)