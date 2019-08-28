package com.piotrprus.quickcurrencies.common.di

import com.piotrprus.quickcurrencies.feature.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}