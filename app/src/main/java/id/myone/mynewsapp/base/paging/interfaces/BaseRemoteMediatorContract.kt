/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.paging.interfaces


interface BaseRemoteMediatorContract<T, Z> {
    suspend fun getRemoteKeyById(id: Int): Z

    suspend fun truncateLocalData()

    suspend fun refreshLocalData(prePage: Int?, nextPage: Int?, data: List<T>)
    suspend fun getDataFromService(page: Int, pageSize: Int): List<T>
}