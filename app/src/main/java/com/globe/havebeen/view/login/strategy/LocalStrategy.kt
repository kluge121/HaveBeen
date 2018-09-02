package com.globe.havebeen.view.login.strategy

import android.content.Context

import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.globe.havebeen.view.login.LoginSelectFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.fragment_default_sign.*

/**
 * Created by baeminsu on 01/09/2018.
 */

class LocalStrategy(var email: String, var password: String) : ILoginStrategy {

    lateinit var context: Context

    override fun login(fragment: Fragment) {
        val auth = FirebaseAuth.getInstance()
        context = fragment.context!!

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(fragment.context, "${auth.currentUser!!} ", Toast.LENGTH_LONG).show()
                        //TODO 인증메일
                        val cUser = FirebaseAuth.getInstance().currentUser
                        userInfoStore(cUser!!.email!!, cUser.displayName!!, cUser.photoUrl.toString(), cUser.uid)
                    } else {
                        task.exception!!.printStackTrace()
                        Toast.makeText(fragment.context,
                                "${task.exception!!.message} ", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    if (it is FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "이미 연동 및 가입된 이메일주소입니다.", Toast.LENGTH_SHORT).show()
                    }

                }

    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailure(exception: Exception?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}