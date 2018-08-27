package com.globe.havebeen.view.login

import android.os.Bundle
import com.globe.havebeen.R
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.base.addFragment
import com.globe.havebeen.view.base.removeFragmentById
import com.globe.havebeen.view.base.replaceFragment
import com.globe.havebeen.view.login.presenter.LoginContract
import com.globe.havebeen.view.login.presenter.LoginDefaultPresenter
import com.globe.havebeen.view.login.presenter.LoginPresenter
import com.globe.havebeen.view.login.presenter.LoginSelectPresenter

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
}