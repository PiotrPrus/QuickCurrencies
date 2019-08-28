package com.piotrprus.quickcurrencies.utils.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.piotrprus.quickcurrencies.common.data.models.Rates

class RatesTypeConverter {

    @TypeConverter
    fun ratesToString(rates: Rates): String =
        Gson().toJson(rates)

    @TypeConverter
    fun stringToRates(string: String): Rates =
        Gson().fromJson(string, Rates::class.java)
}