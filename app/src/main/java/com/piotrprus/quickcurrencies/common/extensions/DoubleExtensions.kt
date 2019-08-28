package com.piotrprus.quickcurrencies.common.extensions

import java.lang.Math.round

fun Double.roundToDecimals(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) {
        multiplier *= 10
    }
    return round(this * multiplier).div(multiplier)
}