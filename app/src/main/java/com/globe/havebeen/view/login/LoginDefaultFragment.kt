package com.globe.havebeen.view.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import com.globe.havebeen.R
import com.globe.havebeen.test.Email
import com.globe.havebeen.view.login.presenter.LoginContract
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_default_sign.*


/**
 * Created by baeminsu on 26/08/2018.
 */

class LoginDefaultFragment : Fragment(), LoginContract.IDefaultView {
    override lateinit var presenter: LoginContract.IDefaultPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(
                R.layout.fragment_default_sign,
                container, false)
        with(root) {

            val loginDefaultSubmitButton: TextView = findViewById(R.id.loginDefaultSubmitButton)
            val loginDefaultForgetText: TextView = findViewById(R.id.loginDefaultForgetText)
            val loginDefaultSignUpToggle: ToggleButton = findViewById(R.id.loginDefaultSignUpToggle)
            loginDefaultSubmitButton.setOnClickListener {
                if (loginDefaultSignUpToggle.isChecked) {
                    // 회원가입
                    registerSubmit()
                } else {
                    // 로그인
                    loginSubmit()
                }
            }
            loginDefaultForgetText.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
            }

            loginDefaultSignUpToggle.setOnClickListener {
                changeLoginSignUp(loginDefaultSignUpToggle.isChecked)
            }
        }

        return root
    }

    override fun verifyLocalCheck(): Boolean {
        // TODO : 이메일 비밀번호 잘 써졌는지 확인해야됨
        return true
    }

    override fun loginSubmit() {
        val auth = FirebaseAuth.getInstance()
        val email = loginDefaultIdEdit.text.toString()
        val password = loginDefaultPasswordEdit.text.toString()
    }

    override fun registerSubmit() {
        loginDefaultSubmitButton.isEnabled = false
        if (!verifyLocalCheck()) {
            loginDefaultSubmitButton.isEnabled = true
            return
        }

        val auth = FirebaseAuth.getInstance()

        val email = loginDefaultIdEdit.text.toString()
        val password = loginDefaultPasswordEdit.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity,
                                "${auth.currentUser!!} ", Toast.LENGTH_LONG).show()
                        Email().emailSend(email, context!!)
                    } else {
                        task.exception?.printStackTrace()
                        Toast.makeText(activity,
                                "${task.exception?.message} ", Toast.LENGTH_LONG).show()
                    }
                }
        loginDefaultSubmitButton.isEnabled = false
    }

    override fun changeLoginSignUp(isChecked: Boolean) {
        if (isChecked) {
            // 로그인 -> 회원가입
            loginDefaultSubmitButton.text = getString(R.string.login_default_submit_signup_text)
            loginDefaultForgetText.visibility = View.GONE
        } else {
            // 회원가입 -> 로그인
            loginDefaultSubmitButton.text = getString(R.string.login_default_submit_login_text)
            loginDefaultForgetText.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    companion object {
        fun newInstance() = LoginDefaultFragment()
    }
}