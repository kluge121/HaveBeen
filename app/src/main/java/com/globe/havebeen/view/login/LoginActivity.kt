package com.globe.havebeen.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.globe.havebeen.R
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.base.addFragment
import com.globe.havebeen.view.base.removeFragmentById
import com.globe.havebeen.view.base.replaceFragment
import com.globe.havebeen.view.login.presenter.LoginContract
import com.globe.havebeen.view.login.presenter.LoginDefaultPresenter
import com.globe.havebeen.view.login.presenter.LoginPresenter
import com.globe.havebeen.view.login.presenter.LoginSelectPresenter
import com.kakao.auth.Session.getCurrentSession

/**
 * Created by baeminsu on 25/08/2018.
 */

class LoginActivity : BaseActivity(), LoginContract.ILoginView, LoginContract.DefaultFragmentListener {


    override lateinit var presenter: LoginContract.ILoginPresenter
    private lateinit var loginSelectPresenter: LoginSelectPresenter
    private var defaultPresenter: LoginDefaultPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val fragment = LoginSelectFragment.newInstance()
        replaceFragment(fragment, R.id.loginFragmentContainer)
        presenter = LoginPresenter(this)
        loginSelectPresenter = LoginSelectPresenter(fragment)
    }

    override fun addDefaultFragment() {
        val fragment = LoginDefaultFragment.newInstance()
        addFragment(fragment, R.id.loginFragmentContainer)
        defaultPresenter = LoginDefaultPresenter(fragment)
    }

    override fun onBackPressed() {
        if (defaultPresenter != null) {
            removeFragmentById(R.id.loginFragmentContainer)
            defaultPresenter = null
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.e("체크22", "체크22")
            return
        }
        Log.e("체크11", "체크11")
    }
}