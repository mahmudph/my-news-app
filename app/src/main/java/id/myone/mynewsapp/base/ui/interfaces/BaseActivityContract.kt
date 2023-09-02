/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.ui.interfaces

import androidx.viewbinding.ViewBinding

interface BaseActivityContract<T: ViewBinding>: BaseContract<T> {
    fun setupViewBinding(): T
}