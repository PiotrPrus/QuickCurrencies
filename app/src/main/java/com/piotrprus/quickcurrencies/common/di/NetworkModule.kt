package com.piotrprus.quickcurrencies.common.di

import com.piotrprus.quickcurrencies.BuildConfig
import com.piotrprus.quickcurrencies.common.data.remote.RevolutCurrenciesApi
import com.piotrprus.quickcurrencies.utils.constants.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofitService<RevolutCurrenciesApi>(get(), Const.REVOLUT_BASE_URL) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
    }

    return builder.build()
}

private inline fun <reified T> provideRetrofitService(okHttpClient: OkHttpClient, url: String): T =
    Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)