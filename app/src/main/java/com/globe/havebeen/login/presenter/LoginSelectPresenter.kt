package com.globe.havebeen.login.presenter

import android.content.Intent
import android.util.Log
import com.facebook.FacebookSdk
import com.globe.havebeen.config.LoginType
import com.globe.havebeen.login.strategy.FaceBookStategy
import com.globe.havebeen.login.strategy.GoogleStrategy
import com.globe.havebeen.login.strategy.ILoginStrategy
import com.globe.havebeen.login.strategy.KakaoStrategy

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
            LoginType.LOCAL -> {
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
