package com.piotrprus.quickcurrencies

import android.app.Application
import com.piotrprus.quickcurrencies.common.di.networkModule
import com.piotrprus.quickcurrencies.common.di.repositoryModule
import com.piotrprus.quickcurrencies.common.di.viewModelModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class QuickCurrencies : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@QuickCurrencies)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
        RxJavaPlugins.setErrorHandler {
            Timber.e(it, "The error not be delivered to callback")
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}