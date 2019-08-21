package com.piotrprus.quickcurrencies.common.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToComposite(composite: CompositeDisposable) {
    composite.add(this)
}