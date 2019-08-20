package com.piotrprus.quickcurrencies.common.data.remote

import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.utils.constants.Const
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutCurrenciesApi {

    @GET("latest")
    fun getRates(@Query("base") code: String = Const.EUR_CODE): Observable<CurrencyBase>
}