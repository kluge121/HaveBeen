package com.globe.havebeen.view.login.presenter

import android.content.Intent
import android.support.v4.app.Fragment
import com.facebook.login.widget.LoginButton
import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView
import com.globe.havebeen.view.login.enums.LoginType

/**
 * Created by baeminsu on 25/08/2018.
 */
interface LoginContract {


    //메인 액티비티
    interface ILoginView : BaseView<ILoginPresenter>
    interface ILoginPresenter : BasePresenter


    //로그인 선택
    interface ILoginSelectView : BaseView<ILoginSelectPresenter> {
        fun getFragment(): Fragment
        fun getFacebookLoginBtn(): LoginButton
        fun defaultLogin()
        fun getLocalEmail(): String
        fun getLocalPassword(): String
    }

    interface ILoginSelectPresenter : BasePresenter {
        fun loginRequest(loginType: LoginType)
        fun onActivityResultForLogin(requestCode: Int, resultCode: Int, data: Intent?)

    }

    //로컬 로그인
    interface IDefaultView : BaseView<IDefaultPresenter> {
        fun changeLoginSignUp(isChecked: Boolean)
        fun verifyLocalCheck(): Boolean
        fun loginSubmit()
        fun registerSubmit()
    }

    interface IDefaultPresenter : BasePresenter


    interface DefaultFragmentListener {
        fun addDefaultFragment()
    }
}