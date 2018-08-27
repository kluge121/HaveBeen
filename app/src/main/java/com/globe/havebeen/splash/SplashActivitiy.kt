package com.globe.havebeen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.globe.havebeen.R
import com.globe.havebeen.base.BaseActivity
import com.globe.havebeen.login.LoginActivity
import com.globe.havebeen.main.MainActivity
import com.globe.havebeen.splash.Presenter.SplashContract
import com.globe.havebeen.splash.Presenter.SplashPresenter
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by baeminsu on 26/08/2018.
 */
class SplashActivity : BaseActivity(), SplashContract.ISplashView {

    override lateinit var presenter: SplashContract.ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(this)
        Handler().postDelayed(Runnable {
            if (FirebaseAuth.getInstance().currentUser != null) {
                Toast.makeText(this, FirebaseAuth.getInstance().currentUser?.email, Toast.LENGTH_SHORT).show()
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