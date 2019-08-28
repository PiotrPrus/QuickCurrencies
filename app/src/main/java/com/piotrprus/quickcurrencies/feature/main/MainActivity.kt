package com.piotrprus.quickcurrencies.feature.main

import android.content.Intent
import android.provider.Settings
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.piotrprus.quickcurrencies.R
import com.piotrprus.quickcurrencies.base.BaseVMActivity
import com.piotrprus.quickcurrencies.common.annotation.LayoutResId
import com.piotrprus.quickcurrencies.common.extensions.onTextChanged
import com.piotrprus.quickcurrencies.common.extensions.showKeyboard
import com.piotrprus.quickcurrencies.databinding.ActivityMainBinding
import com.piotrprus.quickcurrencies.feature.main.rv.CurrencyAdapter
import timber.log.Timber


@LayoutResId(R.layout.activity_main)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    private val adapter: CurrencyAdapter by lazy {
        CurrencyAdapter { viewModel.startObservingRates(it) }
    }
    private val adapterDataObserver: RecyclerView.AdapterDataObserver by lazy {
        object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.currencyRV.scrollToPosition(0)
                binding.baseET.showKeyboard()
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
        viewModel.dataStateLiveData.observe { viewModel.updateViewsVisibility(it) }
        viewModel.baseItemLiveData.observe {
            viewModel.currentCurrencyBase?.let { viewModel.handleResults(it) }
        }
        viewModel.showSnackbarEvent.observeEvent { showSnackbar() }
    }

    private fun showSnackbar() {
        val snackbar = Snackbar.make(binding.root, R.string.snackbar_main_text, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.snackbar_button_text) { openNetworkSettings() }
        snackbar.show()
    }

    private fun openNetworkSettings() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    override fun onDestroy() {
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
        super.onDestroy()
    }
}
