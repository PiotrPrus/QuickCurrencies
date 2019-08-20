package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.base.BaseViewModel
import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import com.piotrprus.quickcurrencies.common.extensions.addToComposite
import com.piotrprus.quickcurrencies.common.extensions.applyIoSchedulers
import timber.log.Timber

class MainViewModel(private val repository: RevolutCurrenciesRepository)
    : BaseViewModel() {

    init {
        repository.fetchRates()
            .applyIoSchedulers()
            .subscribe { Timber.d("currencies result: ${it.rates}") }
            .addToComposite(disposables)

    }
}