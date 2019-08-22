package com.piotrprus.quickcurrencies.common.data.models

import com.piotrprus.quickcurrencies.utils.constants.Const

data class Currency(val name: String, val rate: Double) {
    val fullNameResId: Int
        get() = Const.CURRENCY_LIST.getValue(name).first
    val iconDrawableId: Int
        get() = Const.CURRENCY_LIST.getValue(name).second
}