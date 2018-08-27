package com.globe.havebeen.splash.Presenter

import com.globe.havebeen.base.BasePresenter
import com.globe.havebeen.base.BaseView

/**
 * Created by baeminsu on 26/08/2018.
 */
interface SplashContract {
    interface ISplashView: BaseView<ISplashPresenter> {
    }

    interface ISplashPresenter: BasePresenter {

    }
}