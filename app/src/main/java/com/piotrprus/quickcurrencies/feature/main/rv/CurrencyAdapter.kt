package com.piotrprus.quickcurrencies.feature.main.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.piotrprus.quickcurrencies.R
import com.piotrprus.quickcurrencies.common.data.models.Currency
import com.piotrprus.quickcurrencies.databinding.ItemCurrencyBinding

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.rate == newItem.rate
}

class CurrencyAdapter : ListAdapter<Currency, CurrencyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: ItemCurrencyBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.item_currency, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency: Currency = getItem(position)
        holder.bind(currency)
    }
}