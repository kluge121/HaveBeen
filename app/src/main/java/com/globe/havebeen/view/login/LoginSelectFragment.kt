package com.globe.havebeen.view.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facebook.login.widget.LoginButton
import com.globe.havebeen.R
import com.globe.havebeen.view.login.enums.LoginType
import com.globe.havebeen.view.login.presenter.LoginContract
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import kotlinx.android.synthetic.main.fragment_login_select.*

/**
 * Created by baeminsu on 25/08/2018.
 */


class LoginSelectFragment() : Fragment(), LoginContract.ILoginSelectView {


    private lateinit var defaultFragmentListener: LoginContract.DefaultFragmentListener
    private lateinit var facebookLoginBtn: LoginButton
    private lateinit var googleLoginBtn: SignInButton
    var buttonEnableFlag = true


    override lateinit var presenter: LoginContract.ILoginSelectPresenter

    companion object {
        fun newInstance() = LoginSelectFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_login_select, container, false)
        with(root) {

            //origin btn
            val googleLoginFakeBtn: Button = findViewById(R.id.googleLoginFakeBtn)
            val facebookLoginFakeBtn: Button = findViewById(R.id.facebookLoginFakeBtn)
            val kakaoLoginFakeBtn: Button = findViewById(R.id.kakaoLoginFakeBtn)
            val defaultLoginBtn: Button = findViewById(R.id.defaultLoginBtn)
            //fake btn
            googleLoginBtn = findViewById(R.id.googleLoginBtn)
            facebookLoginBtn = findViewById(R.id.facebookLoginBtn)
            val kakaoLoginBtn: com.kakao.usermgmt.LoginButton = findViewById(R.id.kakaoLoginBtn)

            val loginTest: TextView = findViewById(R.id.loginTest)

            googleLoginBtn.setOnClickListener {
                presenter.loginRequest(LoginType.GOOGLE)
            }
            googleLoginFakeBtn.setOnClickListener {
                presenter.loginRequest(LoginType.GOOGLE)
            }
            facebookLoginFakeBtn.setOnClickListener {
                facebookLoginBtn.performClick()
            }
            facebookLoginBtn.setOnClickListener {
                presenter.loginRequest(LoginType.FACEBOOK)
            }
            kakaoLoginFakeBtn.setOnClickListener {
                presenter.loginRequest(LoginType.KAKAO)
                kakaoLoginBtn.performClick()
            }
            defaultLoginBtn.setOnClickListener {
                defaultLogin()
            }
            loginTest.setOnClickListener {
                kakaoUnlink()
            }
        }
        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is LoginContract.DefaultFragmentListener) {
            defaultFragmentListener = context
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.onActivityResultForLogin(requestCode, resultCode, data)
        Log.e("리퀘스트코드", "$requestCode")
        Log.e("리줄트코드", "$resultCode")
    }

    override fun getFragment(): Fragment {
        return this
    }

    override fun getFacebookLoginBtn(): LoginButton {
        return facebookLoginBtn
    }

    override fun defaultLogin() {
        defaultFragmentListener.addDefaultFragment()
    }


    private fun kakaoUnlink() {
        val appendMessage = getString(R.string.summit)
        AlertDialog.Builder(context!!)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        DialogInterface.OnClickListener { dialog, which ->
                            UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
                                override fun onSuccess(result: Long?) {
                                    Toast.makeText(context, "연결이 해지되었습니다", Toast.LENGTH_SHORT).show()
                                }

                                override fun onSessionClosed(errorResult: ErrorResult?) {
                                    Toast.makeText(context, errorResult.toString(), Toast.LENGTH_SHORT).show()
                                }

                                override fun onNotSignedUp() {
                                    Toast.makeText(context, "연결이 22", Toast.LENGTH_SHORT).show()
                                }

                            })
                            dialog.dismiss()
                        }).setNegativeButton(getString(R.string.cancel)
                ) { dialog, which -> dialog!!.dismiss() }.show()
    }


    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.e("인증성공", "성공")
                            loginTest.text = "로그인 인증 성공"
                        } else {
                            Log.e("인증실패", "실패")
                            loginTest.text = "로그인 인증 실패"
                        }
                    }
        }
    }

    fun buttonEnableControl() {

    }
}
