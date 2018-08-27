package com.globe.havebeen.login.presenter

/**
 * Created by baeminsu on 25/08/2018.
 */
class LoginPresenter(loginView: LoginContract.ILoginView) : LoginContract.ILoginPresenter {

    init {
        loginView.presenter = this
    }
    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}