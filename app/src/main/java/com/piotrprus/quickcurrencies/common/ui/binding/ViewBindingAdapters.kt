package com.piotrprus.quickcurrencies.common.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun isViewVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}