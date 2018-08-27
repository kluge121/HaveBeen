package com.globe.havebeen.test

import android.content.Intent
import android.util.Log
import com.globe.havebeen.constants.EMAIL_CHECK_TMP_STROE
import com.globe.havebeen.data.preferences.TraySharedPreference
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import com.google.firebase.auth.AuthResult

/**
 * Created by baeminsu on 26/08/2018.
 */
class Email {


    fun emailSend(email: String, context: Context) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user!!.sendEmailVerification()
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Log.e("이메일 보내", "빨리 보내")
                }
    }
    
    fun emailCheck(intent: Intent, context: Context) {

        val auth = FirebaseAuth.getInstance()
        val emailLink = intent.data
        val email = TraySharedPreference(context).getString(EMAIL_CHECK_TMP_STROE)

        if (auth.isSignInWithEmailLink(emailLink.toString())) {
            auth.signInWithEmailLink(email!!, emailLink.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.e("인증여부", "성공")
                            val reuslt: AuthResult = it.result
                        } else {
                            Log.e("인증여부", "실패 ${it.exception!!.message}")
                        }
                    }

        }

    }

}