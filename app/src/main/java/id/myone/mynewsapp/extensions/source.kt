/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.extensions

import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.model.repository.datasource.remote.model.SourceData

fun SourceData.mapToEntity(): SourceEntity {
    return SourceEntity(
        sourceId = id,
        category = category,
        country = country,
        description = description,
        language = language,
        name = name,
        url = url
    )
}