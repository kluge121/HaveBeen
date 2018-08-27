package com.globe.havebeen.splash.Presenter

/**
 * Created by baeminsu on 26/08/2018.
 */
class SplashPresenter(splashView: SplashContract.ISplashView)
    : SplashContract.ISplashPresenter {

    init {
        splashView.presenter = this
    }

    override fun start() {

    }
}