package com.piotrprus.quickcurrencies.common.data.repository

import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.models.dao.CurrencyDao
import io.reactivex.Completable
import io.reactivex.Single

class DbCurrenciesRepository(private val currencyDao: CurrencyDao) {
    fun getBaseItem(): Single<CurrencyBase> = currencyDao.getAll()
    fun addBaseItem(item: CurrencyBase?): Completable =
        item?.let {
            currencyDao.addAll(item)
        } ?: Completable.complete()
}