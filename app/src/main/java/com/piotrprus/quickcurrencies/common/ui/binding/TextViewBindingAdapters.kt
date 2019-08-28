package com.piotrprus.quickcurrencies.common.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textResId")
fun setTextFromResourceId(view: TextView, resId: Int) {
    val textFromId = view.context.resources.getText(resId)
    view.text = textFromId
}