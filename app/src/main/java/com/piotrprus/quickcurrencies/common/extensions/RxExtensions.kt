package com.piotrprus.quickcurrencies.common.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.addToComposite(composite: CompositeDisposable) {
    composite.add(this)
}

fun <T> Observable<T>.applyIoSchedulers(): Observable<T> {
    return this.compose { upstream ->
        upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}