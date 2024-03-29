package com.piotrprus.quickcurrencies.feature.main

import androidx.lifecycle.MutableLiveData
import com.piotrprus.quickcurrencies.base.BaseViewModel
import com.piotrprus.quickcurrencies.common.data.models.Currency
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.models.DataState
import com.piotrprus.quickcurrencies.common.data.repository.DbCurrenciesRepository
import com.piotrprus.quickcurrencies.common.data.repository.RevolutCurrenciesRepository
import com.piotrprus.quickcurrencies.common.extensions.addToComposite
import com.piotrprus.quickcurrencies.utils.constants.Const
import com.piotrprus.quickcurrencies.utils.event.DataEventEmitter
import com.piotrprus.quickcurrencies.utils.event.EventEmitter
import com.piotrprus.quickcurrencies.utils.event.emit
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.reflect.full.memberProperties

class MainViewModel(
    private val repository: RevolutCurrenciesRepository,
    private val dbRepository: DbCurrenciesRepository
) : BaseViewModel() {

    val submitListEvent = DataEventEmitter<List<Currency>>()
    private var apiDisposable: Disposable? = null
    val baseItemLiveData = MutableLiveData<Currency>()
    val dataStateLiveData = MutableLiveData<DataState>()
    val progressBarVisibility = MutableLiveData<Boolean>()
    val dataVisibility = MutableLiveData<Boolean>()
    val failureViewVisibility = MutableLiveData<Boolean>()
    var currentCurrencyBase: CurrencyBase? = null
    val showSnackbarEvent = EventEmitter()

    init {
        dataStateLiveData.value = DataState.LOADING
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
            .subscribe({ handleResults(it) }, {
                Timber.d(it, "Fetching currency data failed, trying to load from db")
                loadDataFromDb(currencyCode)
            })
    }

    fun handleResults(item: CurrencyBase) {
        saveDataToDb(item)
        currentCurrencyBase = item
        val list = mutableListOf<Currency>()
        for (prop in item.rates::class.memberProperties) {
            val rateValue = prop.getter.call(item.rates) as Double
            val rateValueCalculated = baseItemLiveData.value?.rate?.times(rateValue) ?: rateValue
            if (prop.name != item.base) list.add(Currency(prop.name, rateValueCalculated))
        }
        if (baseItemLiveData.value?.name != item.base) baseItemLiveData.value = Currency(item.base, 1.0)
        submitListEvent.emit(list)
        dataStateLiveData.value = DataState.LOADED
    }

    private fun saveDataToDb(item: CurrencyBase) {
        dbRepository.addBaseItem(item)
            .subscribeOn(Schedulers.io())
            .subscribe { Timber.d("CurrencyBase inserted into DB") }
            .addToComposite(disposables)
    }

    private fun loadDataFromDb(currencyCode: String?) {
        showSnackbarEvent.emit()
        if (currencyCode != null) return
        dbRepository.getBaseItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                handleResults(item)
                Timber.d("Item from db: ${item.base}")
            },
                {
                    Timber.d(it, "Fetching data from db failed.")
                    dataStateLiveData.value = DataState.FAILED
                })
            .addToComposite(disposables)
    }

    fun updateViewsVisibility(state: DataState?) {
        when (state) {
            DataState.LOADING -> {
                progressBarVisibility.value = true
                dataVisibility.value = false
                failureViewVisibility.value = false
            }
            DataState.LOADED -> {
                progressBarVisibility.value = false
                dataVisibility.value = true
                failureViewVisibility.value = false
            }
            DataState.FAILED, null -> {
                progressBarVisibility.value = false
                dataVisibility.value = false
                failureViewVisibility.value = true
            }
        }
    }

    fun updateBaseRate(changedRate: String) {
        baseItemLiveData.value = baseItemLiveData.value.let { currency ->
            currency?.copy(rate = changedRate.toDoubleOrNull() ?: 1.0)
        }
    }

    fun onTryAgainClicked() {
        val baseCode = baseItemLiveData.value?.name ?: Const.EUR_CODE
        dataStateLiveData.value = DataState.LOADING
        startObservingRates(baseCode)
    }

    override fun onCleared() {
        apiDisposable?.dispose()
        super.onCleared()
    }
}