/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp

import android.app.Application
import id.myone.mynewsapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyNewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyNewsApp)
            modules(appModules)
        }
    }
}