package com.piotrprus.quickcurrencies.feature.main

import com.piotrprus.quickcurrencies.R
import com.piotrprus.quickcurrencies.base.BaseVMActivity
import com.piotrprus.quickcurrencies.common.annotation.LayoutResId
import com.piotrprus.quickcurrencies.databinding.ActivityMainBinding

@LayoutResId(R.layout.activity_main)
class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    override fun start() {
        super.start()
        binding.viewModel = viewModel
    }
}
