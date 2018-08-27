package com.globe.havebeen

/**
 * Created by baeminsu on 25/08/2018.
 */
interface BasePresenter {
    fun start()
}

interface BaseView<T> {
    var presenter: T
}