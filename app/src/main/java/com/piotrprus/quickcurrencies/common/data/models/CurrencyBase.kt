package com.piotrprus.quickcurrencies.common.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyBase(
    @PrimaryKey val base: String,
    val date: String,
    val rates: Rates
)