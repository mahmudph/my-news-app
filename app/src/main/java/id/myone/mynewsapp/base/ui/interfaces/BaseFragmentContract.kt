/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.ui.interfaces

import android.view.LayoutInflater
import android.view.ViewGroup

interface BaseFragmentContract<T>: BaseContract<T> {
    fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?,): T
}