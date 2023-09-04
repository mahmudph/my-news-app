/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces


interface BaseRemoteKeyEntity : BaseEntity {
    val prePage: Int?
    val nexPage: Int?
}