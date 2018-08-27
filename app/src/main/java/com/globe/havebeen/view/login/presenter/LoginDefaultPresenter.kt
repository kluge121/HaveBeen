package com.globe.havebeen.view.login.presenter

/**
 * Created by baeminsu on 26/08/2018.
 */
class LoginDefaultPresenter(
        defaultView: LoginContract.IDefaultView
) : LoginContract.IDefaultPresenter {

    init {
        defaultView.presenter = this
    }

    override fun start() {
    }
}