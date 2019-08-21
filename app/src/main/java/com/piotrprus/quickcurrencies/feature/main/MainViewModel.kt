package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.base.BaseViewModel
import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import com.piotrprus.quickcurrencies.common.extensions.addToComposite
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(private val repository: RevolutCurrenciesRepository) : BaseViewModel() {

    init {
        startObservingRates()
    }

    private fun startObservingRates() {
        Flowable
            .interval(1, TimeUnit.SECONDS)
            .onBackpressureLatest()
            .concatMapSingle { repository.fetchRates() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ Timber.d("${it.rates}") }, {})
            .addToComposite(disposables)
    }
}