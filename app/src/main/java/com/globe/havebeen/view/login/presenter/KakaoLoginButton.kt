package com.globe.havebeen.view.login.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.kakao.usermgmt.LoginButton

/**
 * Created by baeminsu on 27/08/2018.
 */
class KakaoLoginButton : LoginButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setLoginResult() {
        val intent = Intent()
        (context as Activity).setResult(30, intent)
    }

}