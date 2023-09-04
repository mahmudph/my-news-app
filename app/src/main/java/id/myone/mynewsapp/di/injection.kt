/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.di

import androidx.room.Room
import id.myone.mynewsapp.common.apiKey
import id.myone.mynewsapp.common.dbName
import id.myone.mynewsapp.common.hostUrl
import id.myone.mynewsapp.model.repository.AppRepository
import id.myone.mynewsapp.model.repository.AppRepositoryContract
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.remote.AppService
import id.myone.mynewsapp.view.widget.LoadingDialog
import id.myone.mynewsapp.viewmodel.DetailArticleViewModel
import id.myone.mynewsapp.viewmodel.ListArticleViewModel
import id.myone.mynewsapp.viewmodel.SourceViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appNetworkModule = module {
    single {
        var client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("apiKey", apiKey)
                    .build()

                chain.proceed(request.newBuilder().url(url).build())
            }

        client = client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        client.build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(hostUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AppService::class.java)
    }

    single<AppRepositoryContract> { AppRepository(get(), get(), get(), get()) }
}


val appDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, dbName
        ).build()
    }

    single { get<AppDatabase>().articleDao() }
    single { get<AppDatabase>().sourceDao() }
}

val utilityModule = module {
    fragment { LoadingDialog.newInstance() }
}

val appViewModels = module {
    viewModel { ListArticleViewModel(get()) }
    viewModel { DetailArticleViewModel(get()) }
    viewModel { SourceViewModel(get()) }
}

val appModules = listOf(appNetworkModule, appDatabaseModule, appViewModels, utilityModule)