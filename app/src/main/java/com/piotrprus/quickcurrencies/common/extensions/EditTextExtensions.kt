package com.piotrprus.quickcurrencies.common.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.onTextChanged(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            text?.let { callback.invoke(text.toString()) }
        }
    })
}

fun EditText.showKeyboard() {
    if (requestFocus()) {
        (getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        setSelection(text.length)
    }
}