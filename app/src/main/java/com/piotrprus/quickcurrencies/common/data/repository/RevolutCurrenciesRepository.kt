package com.piotrprus.quickcurrencies.common.data.repository

import com.piotrprus.quickcurrencies.common.data.remote.RevolutCurrenciesApi
import com.piotrprus.quickcurrencies.utils.constants.Const

class RevolutCurrenciesRepository(private val currenciesApi: RevolutCurrenciesApi) {
    fun fetchRates(code: String = Const.EUR_CODE) = currenciesApi.getRates(code)
}