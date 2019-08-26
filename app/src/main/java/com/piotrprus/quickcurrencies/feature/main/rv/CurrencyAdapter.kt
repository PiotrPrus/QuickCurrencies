package com.piotrprus.quickcurrencies.feature.main.rv

import android.os.Bundle
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
        oldItem == newItem

    override fun getChangePayload(oldItem: Currency, newItem: Currency): Bundle? {
        val bundle = Bundle()
        if (oldItem.iconDrawableId != newItem.iconDrawableId) {
            bundle.putInt("imageResId", newItem.iconDrawableId)
        }
        if (oldItem.fullNameResId != newItem.fullNameResId) {
            bundle.putInt("fullNameResId", newItem.fullNameResId)
        }
        if (oldItem.name != newItem.name) {
            bundle.putString("currencyName", newItem.name)
        }
        return bundle
    }
}

class CurrencyAdapter(private val onClickFunction: (String) -> Unit) :
    ListAdapter<Currency, CurrencyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: ItemCurrencyBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.item_currency, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency: Currency = getItem(position)
        holder.bind(currency, onClickFunction)
    }
}