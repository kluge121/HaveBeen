package com.globe.havebeen.view.login.strategy

import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.globe.havebeen.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.raizlabs.android.dbflow.kotlinextensions.exists

/**
 * Created by baeminsu on 25/08/2018.
 */
interface ILoginStrategy {

    fun login(fragment: Fragment)
    fun onLoginSuccess()
    fun onLoginFailure(exception: Exception?): String
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    fun userInfoStore(email: String, nickname: String, profileURL: String, uid: String) {
        val user = User(email, nickname, profileURL)
        val db = FirebaseFirestore.getInstance()


        db.collection("user").document(uid).get()
                .addOnCompleteListener {
                    val document = it.result
                    if (document.exists()) {
                        Log.e("파이어베이스체크", "존재")
                    } else {
                        Log.e("파이어베이스체크", "미존재")
                        db.collection("user")
                                .document(uid)
                                .set(user)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {

                                    } else {

                                    }
                                }
                    }
                }

    }


}