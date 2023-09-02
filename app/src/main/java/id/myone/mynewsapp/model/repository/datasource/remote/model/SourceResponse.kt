package id.myone.mynewsapp.model.repository.datasource.remote.model

import androidx.annotation.Keep

@Keep
data class SourceResponse(
    val sources: List<SourceData>,
    val status: String
)