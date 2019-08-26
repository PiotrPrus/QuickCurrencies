package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.base.BaseViewModel
import com.piotrprus.quickcurrencies.common.data.models.Currency
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import com.piotrprus.quickcurrencies.utils.constants.Const
import com.piotrprus.quickcurrencies.utils.event.DataEventEmitter
import com.piotrprus.quickcurrencies.utils.event.emit
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.reflect.full.memberProperties

class MainViewModel(private val repository: RevolutCurrenciesRepository) : BaseViewModel() {

    val submitListEvent = DataEventEmitter<List<Currency>>()
    var currentBaseCode: String = Const.EUR_CODE
    private var apiDisposable: Disposable? = null

    init {
        startObservingRates(null)
    }

    fun startObservingRates(currencyCode: String?) {
        apiDisposable?.dispose()
        apiDisposable = Flowable
            .interval(1, TimeUnit.SECONDS)
            .onBackpressureLatest()
            .concatMapSingle { repository.fetchRates(currencyCode) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { handleResults(it) }
    }

    private fun handleResults(item: CurrencyBase) {
        val list = mutableListOf<Currency>()
        for (prop in item.rates::class.memberProperties) {
            val rateValue = prop.getter.call(item.rates) as Double
            if (prop.name != item.base) list.add(Currency(prop.name, rateValue))
        }
        list.add(0, Currency(item.base, 1.0))
        submitListEvent.emit(list)
        currentBaseCode = item.base
    }

    override fun onCleared() {
        apiDisposable?.dispose()
        super.onCleared()
    }
}