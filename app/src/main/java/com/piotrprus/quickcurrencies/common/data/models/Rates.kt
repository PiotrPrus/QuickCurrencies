package com.piotrprus.quickcurrencies.common.data.models

import com.google.gson.annotations.SerializedName

data class Rates (
    @SerializedName("AUD") val aud : Double,
    @SerializedName("BGN") val bgn : Double,
    @SerializedName("BRL") val brl : Double,
    @SerializedName("CAD") val cad : Double,
    @SerializedName("CHF") val chf : Double,
    @SerializedName("CNY") val cny : Double,
    @SerializedName("CZK") val czk : Double,
    @SerializedName("DKK") val dkk : Double,
    @SerializedName("GBP") val gbp : Double,
    @SerializedName("HKD") val hkd : Double,
    @SerializedName("HRK") val hrk : Double,
    @SerializedName("HUF") val huf : Double,
    @SerializedName("IDR") val idr : Double,
    @SerializedName("ILS") val ils : Double,
    @SerializedName("INR") val inr : Double,
    @SerializedName("ISK") val isk : Double,
    @SerializedName("JPY") val jpy : Double,
    @SerializedName("KRW") val krw : Double,
    @SerializedName("MXN") val mxn : Double,
    @SerializedName("MYR") val myr : Double,
    @SerializedName("NOK") val nok : Double,
    @SerializedName("NZD") val nzd : Double,
    @SerializedName("PHP") val php : Double,
    @SerializedName("PLN") val pln : Double,
    @SerializedName("RON") val ron : Double,
    @SerializedName("RUB") val rub : Double,
    @SerializedName("SEK") val sek : Double,
    @SerializedName("SGD") val sgd : Double,
    @SerializedName("THB") val thb : Double,
    @SerializedName("TRY") val tRY : Double,
    @SerializedName("USD") val usd : Double,
    @SerializedName("ZAR") val zar : Double,
    @SerializedName("EUR") val eur : Double
)