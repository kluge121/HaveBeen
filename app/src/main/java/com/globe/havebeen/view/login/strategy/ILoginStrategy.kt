package com.globe.havebeen.view.login.strategy

import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Created by baeminsu on 25/08/2018.
 */
interface ILoginStrategy {

    fun login(fragment: Fragment)
    fun onLoginSuccess()
    fun onLoginFailure()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)


}