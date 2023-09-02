/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.utils

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if(!hasBeenHandled) {
            hasBeenHandled = true
            content
        } else null
    }

    fun getContent(): T = content
}