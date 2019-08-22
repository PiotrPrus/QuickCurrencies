package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.base.BaseViewModel
import com.piotrprus.quickcurrencies.common.data.models.Currency
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import com.piotrprus.quickcurrencies.common.extensions.addToComposite
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.reflect.full.memberProperties

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
            .subscribe({ handleResults(it) }, {})
            .addToComposite(disposables)
    }

    private fun handleResults(item: CurrencyBase) {
        val startTime = System.currentTimeMillis()
        val currencyList = mutableListOf<Currency>()
        for (prop in item.rates::class.memberProperties) {
            val rateValue = prop.getter.call(item.rates) as Double
            currencyList.add(Currency(prop.name, rateValue))
        }
        Timber.d("Handle results took: ${System.currentTimeMillis().minus(startTime)} and listSize: ${currencyList.size}")
    }
}