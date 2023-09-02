/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.paging.interfaces

interface BaseDataSourceContract<T> {
    suspend fun loadDataFromService(position: Int, loadSize: Int): List<T>
}