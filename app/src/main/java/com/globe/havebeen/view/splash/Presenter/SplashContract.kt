package com.globe.havebeen.view.splash.Presenter

import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView

/**
 * Created by baeminsu on 26/08/2018.
 */
interface SplashContract {
    interface ISplashView: BaseView<ISplashPresenter> {
    }

    interface ISplashPresenter: BasePresenter {

    }
}