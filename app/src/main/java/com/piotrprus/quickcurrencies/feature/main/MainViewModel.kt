package com.piotrprus.quickcurrencies.feature.main

import androidx.lifecycle.MutableLiveData
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
    private var apiDisposable: Disposable? = null
    val baseItemLiveData = MutableLiveData<Currency>()

    init {
        baseItemLiveData.value = Currency(Const.EUR_CODE, 1.0)
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
            val rateValueCalculated = baseItemLiveData.value?.rate?.times(rateValue) ?: rateValue
            if (prop.name != item.base) list.add(Currency(prop.name, rateValueCalculated))
        }
        if (baseItemLiveData.value?.name != item.base) baseItemLiveData.value = Currency(item.base, 1.0)
        submitListEvent.emit(list)
    }

    override fun onCleared() {
        apiDisposable?.dispose()
        super.onCleared()
    }

    fun updateBaseRate(changedRate: String) {
        baseItemLiveData.value = baseItemLiveData.value.let { currency ->
            currency?.copy(rate = changedRate.toDoubleOrNull() ?: 1.0)
        }
    }
}