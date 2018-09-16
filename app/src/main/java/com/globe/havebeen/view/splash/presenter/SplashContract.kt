package com.globe.havebeen.view.splash.presenter

import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView
import android.content.Context

/**
 * Created by baeminsu on 26/08/2018.
 */
interface SplashContract {
    interface ISplashView: BaseView<ISplashPresenter> {
    }

    interface ISplashPresenter: BasePresenter {

        fun initCityList(context: Context)

    }
}