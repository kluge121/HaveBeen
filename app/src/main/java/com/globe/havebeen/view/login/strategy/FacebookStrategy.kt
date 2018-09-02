package com.globe.havebeen.view.login.strategy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.globe.havebeen.view.login.enums.LoginType
import com.globe.havebeen.view.login.presenter.LoginContract
import com.globe.havebeen.view.main.MainActivity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Created by baeminsu on 25/08/2018.
 */
class FacebookStrategy : ILoginStrategy {


    lateinit var callBackManager: CallbackManager
    lateinit var context: Context

    override fun login(fragment: Fragment) {

        context = fragment.context!!
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

    override fun onLoginFailure(exception: Exception?): String {
        return ""
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as Activity).finish()
                val cUser = FirebaseAuth.getInstance().currentUser
                userInfoStore(cUser!!.email!!, cUser.displayName!!, cUser.photoUrl.toString(), cUser.uid)

            } else {
                Log.e("페이스북 로그인", it.exception.toString())
                if (it.exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(context, "이미 연동 및 가입된 이메일주소입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}