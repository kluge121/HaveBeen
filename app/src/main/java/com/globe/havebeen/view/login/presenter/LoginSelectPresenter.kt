package com.globe.havebeen.view.login.presenter

import android.content.Intent
import android.util.Log
import com.globe.havebeen.view.login.enums.LoginType
import com.globe.havebeen.view.login.strategy.FaceBookStategy
import com.globe.havebeen.view.login.strategy.GoogleStrategy
import com.globe.havebeen.view.login.strategy.ILoginStrategy
import com.globe.havebeen.view.login.strategy.KakaoStrategy

/**
 * Created by baeminsu on 25/08/2018.
 */
class LoginSelectPresenter(val loginSelectView: LoginContract.ILoginSelectView)
    : LoginContract.ILoginSelectPresenter {


    init {
        loginSelectView.presenter = this
    }

    lateinit var loginStrategy: ILoginStrategy;

    override fun loginRequest(loginType: LoginType) {
        when (loginType) {
            LoginType.GOOGLE -> {
                loginStrategy = GoogleStrategy()
            }
            LoginType.KAKAO -> {
                loginStrategy = KakaoStrategy()
            }
            LoginType.FACEBOOK -> {
                loginStrategy = FaceBookStategy()
            }
            else -> {

            }
        }
        loginStrategy.login(loginSelectView.getFragment())
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResultForLogin(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("체크", "체크")
        loginStrategy.onActivityResult(requestCode, resultCode, data!!)
    }

}
