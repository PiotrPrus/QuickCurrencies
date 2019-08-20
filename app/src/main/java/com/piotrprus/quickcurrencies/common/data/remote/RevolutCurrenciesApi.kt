package com.piotrprus.quickcurrencies.common.data.remote

import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutCurrenciesApi {

    @GET("latest")
    fun getRates(@Query("base") code: String): Observable<CurrencyBase>
}