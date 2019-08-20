package com.piotrprus.quickcurrencies.common.di

import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RevolutCurrenciesRepository(get()) }
}