package com.globe.havebeen.view.login.strategy

import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.globe.havebeen.view.login.enums.LoginType
import com.globe.havebeen.view.login.presenter.LoginContract
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by baeminsu on 25/08/2018.
 */
class FaceBookStategy : ILoginStrategy {

    lateinit var callBackManager: CallbackManager

    override fun login(fragment: Fragment) {

        callBackManager = CallbackManager.Factory.create()
        val loginSelectFragment = fragment as LoginContract.ILoginSelectView
        val loginBtn = loginSelectFragment.getFacebookLoginBtn()
        loginBtn.fragment = fragment
        loginBtn.setReadPermissions("email", "public_profile")
        loginBtn.registerCallback(callBackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)
            }

            override fun onCancel() {
                //TODO 취소선택
            }

            override fun onError(error: FacebookException?) {
               //TODO 에러 발생시
            }

        })

    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            LoginType.FACEBOOK.type -> {
                callBackManager.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("페이스북 로그인", "페이스북 로그인 credential : success");
            } else {
                Log.d("페이스북 로그인", "페이스북 로그인 credential : fail");
            }
        }
    }

}