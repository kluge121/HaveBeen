package com.globe.havebeen.view.login.presenter

import android.content.Intent
import android.util.Log
import com.globe.havebeen.view.login.enums.LoginType
import com.globe.havebeen.view.login.strategy.*

/**
 * Created by baeminsu on 25/08/2018.
 */
class LoginSelectPresenter(private val loginSelectView: LoginContract.ILoginSelectView)
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
                loginStrategy = FacebookStrategy()
            }
            LoginType.LOCAL -> {
                loginStrategy = LocalStrategy(loginSelectView.getLocalEmail(), loginSelectView.getLocalPassword())
            }
        }
        loginStrategy.login(loginSelectView.getFragment())
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResultForLogin(requestCode: Int, resultCode: Int, data: Intent?) {
        loginStrategy.onActivityResult(requestCode, resultCode, data!!)
    }

}
