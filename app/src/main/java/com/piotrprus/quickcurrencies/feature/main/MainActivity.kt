package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.R
import com.piotrprus.quickcurrencies.base.BaseVMActivity
import com.piotrprus.quickcurrencies.common.annotation.LayoutResId
import com.piotrprus.quickcurrencies.databinding.ActivityMainBinding
import com.piotrprus.quickcurrencies.feature.main.rv.CurrencyAdapter

@LayoutResId(R.layout.activity_main)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    override fun start() {
        super.start()
        val adapter = CurrencyAdapter { viewModel.startObservingRates(it) }
        binding.currencyRV.adapter = adapter
        binding.viewModel = viewModel
        viewModel.submitListEvent.observeEvent { adapter.submitList(it) }
    }
}
