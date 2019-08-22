package com.piotrprus.quickcurrencies.common.data.repository

import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.remote.RevolutCurrenciesApi
import com.piotrprus.quickcurrencies.utils.constants.Const
import io.reactivex.Single

class RevolutCurrenciesRepository(private val currenciesApi: RevolutCurrenciesApi) {
    fun fetchRates(code: String?): Single<CurrencyBase> {
        val currencyCode = code ?: Const.EUR_CODE
        return currenciesApi.getRates(currencyCode)
    }
}