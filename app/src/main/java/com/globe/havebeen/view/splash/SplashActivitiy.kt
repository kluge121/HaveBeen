package com.globe.havebeen.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.globe.havebeen.R
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.login.LoginActivity
import com.globe.havebeen.view.main.MainActivity
import com.globe.havebeen.view.splash.presenter.SplashContract
import com.globe.havebeen.view.splash.presenter.SplashPresenter
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm

/**
 * Created by baeminsu on 26/08/2018.
 */
class SplashActivity : BaseActivity(), SplashContract.ISplashView {

    override lateinit var presenter: SplashContract.ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        presenter = SplashPresenter(this)
        presenter.initCityList(this)
        Handler().postDelayed(Runnable {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

}