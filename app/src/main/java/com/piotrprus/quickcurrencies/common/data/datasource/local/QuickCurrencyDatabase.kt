package com.piotrprus.quickcurrencies.common.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.models.dao.CurrencyDao
import com.piotrprus.quickcurrencies.utils.typeconverters.RatesTypeConverter

@TypeConverters(RatesTypeConverter::class)
@Database(entities = [CurrencyBase::class], version = 1)
abstract class QuickCurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}