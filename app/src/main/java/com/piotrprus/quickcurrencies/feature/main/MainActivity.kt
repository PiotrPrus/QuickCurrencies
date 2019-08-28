package com.piotrprus.quickcurrencies.feature.main

import androidx.recyclerview.widget.RecyclerView
import com.piotrprus.quickcurrencies.base.BaseVMActivity
import com.piotrprus.quickcurrencies.common.annotation.LayoutResId
import com.piotrprus.quickcurrencies.common.extensions.onTextChanged
import com.piotrprus.quickcurrencies.databinding.ActivityMainBinding
import com.piotrprus.quickcurrencies.feature.main.rv.CurrencyAdapter


@LayoutResId(com.piotrprus.quickcurrencies.R.layout.activity_main)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    private val adapter: CurrencyAdapter by lazy { CurrencyAdapter { viewModel.startObservingRates(it) } }
    private val adapterDataObserver: RecyclerView.AdapterDataObserver by lazy {
        object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                if (toPosition == 0) binding.currencyRV.scrollToPosition(0)
            }
        }
    }

    override fun start() {
        super.start()
        adapter.registerAdapterDataObserver(adapterDataObserver)
        binding.currencyRV.adapter = adapter
        binding.viewModel = viewModel
        viewModel.submitListEvent.observeEvent { adapter.submitList(it) }
        binding.baseET.onTextChanged { viewModel.updateBaseRate(it) }
    }

    override fun onDestroy() {
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
        super.onDestroy()
    }
}
