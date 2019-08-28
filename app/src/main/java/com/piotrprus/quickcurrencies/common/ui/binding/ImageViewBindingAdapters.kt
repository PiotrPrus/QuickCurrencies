package com.piotrprus.quickcurrencies.common.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageSrcId")
fun setImageFromResourceId(view: ImageView, resId: Int) {
    val drawable = view.context.resources.getDrawable(resId)
    view.setImageDrawable(drawable)
}