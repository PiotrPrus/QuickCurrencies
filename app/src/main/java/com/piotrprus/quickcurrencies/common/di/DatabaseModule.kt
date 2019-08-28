package com.piotrprus.quickcurrencies.common.di

import androidx.room.Room
import com.piotrprus.quickcurrencies.common.data.datasource.local.QuickCurrencyDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), QuickCurrencyDatabase::class.java, "qc-data")
            .build()
    }
    single { get<QuickCurrencyDatabase>().currencyDao() }
}