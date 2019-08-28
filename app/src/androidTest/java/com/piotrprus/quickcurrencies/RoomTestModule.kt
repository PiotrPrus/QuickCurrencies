package com.piotrprus.quickcurrencies

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.piotrprus.quickcurrencies.common.data.datasource.local.QuickCurrencyDatabase
import org.koin.dsl.module

val roomTestModule = module {
    single { ApplicationProvider.getApplicationContext() as Context }
    single {
        Room.inMemoryDatabaseBuilder(get(), QuickCurrencyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    single { get<QuickCurrencyDatabase>().currencyDao() }
}

val testModules = listOf(roomTestModule)