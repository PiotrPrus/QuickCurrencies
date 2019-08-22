package com.piotrprus.quickcurrencies.feature.main.rv

import androidx.recyclerview.widget.RecyclerView
import com.piotrprus.quickcurrencies.common.data.models.Currency
import com.piotrprus.quickcurrencies.databinding.ItemCurrencyBinding

class CurrencyViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(currency: Currency, onClickFunction: (String) -> Unit){
        binding.currencyNameMain.text = currency.name.toUpperCase()
        binding.currencyNameSecondary.text = binding.root.context.getString(currency.fullNameResId)
        binding.currencySymbolIV.setImageDrawable(binding.root.context.getDrawable(currency.iconDrawableId))
        binding.currencyET.text = currency.rate.toString()
        binding.root.setOnClickListener { onClickFunction(currency.name) }
    }
}