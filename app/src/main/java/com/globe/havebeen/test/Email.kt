package com.globe.havebeen.test

import android.content.Intent
import android.util.Log
import com.globe.havebeen.constants.EMAIL_CHECK_TMP_STROE
import com.globe.havebeen.util.TraySharedPreference
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import com.google.firebase.auth.AuthResult

/**
 * Created by baeminsu on 26/08/2018.
 */
class Email {


    fun emailSend(email: String, context: Context) {

        val actionCodeSettings: ActionCodeSettings =
                ActionCodeSettings.newBuilder()
                        .setUrl("https://globe.page.link/register_auth")
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName("com.globe.havebeen",
                                true, "12")
                        .build()


        val auth = FirebaseAuth.getInstance()
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e("인증메일전송", "인증메일")
                    }
                }
        TraySharedPreference(context).put(EMAIL_CHECK_TMP_STROE, email)
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