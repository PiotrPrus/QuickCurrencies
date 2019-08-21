package com.piotrprus.quickcurrencies.common.data.remote

import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutCurrenciesApi {

    @GET("latest")
    fun getRates(@Query("base") code: String): Single<CurrencyBase>
}