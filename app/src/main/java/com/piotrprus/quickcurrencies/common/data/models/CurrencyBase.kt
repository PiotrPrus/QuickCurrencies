package com.piotrprus.quickcurrencies.common.data.models

data class CurrencyBase(
    val base: String,
    val date: String,
    val rates: Rates
)