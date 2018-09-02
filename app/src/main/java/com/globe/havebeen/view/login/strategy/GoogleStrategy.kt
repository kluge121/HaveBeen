package com.globe.havebeen.view.login.strategy

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import com.globe.havebeen.R
import com.globe.havebeen.view.login.enums.LoginType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.content.Context
import android.widget.Toast
import com.globe.havebeen.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuthUserCollisionException

/**
 * Created by baeminsu on 25/08/2018.
 */

class GoogleStrategy : ILoginStrategy {

    lateinit var context: Context

    override fun login(fragment: Fragment) {
        context = fragment.context!!
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .apply {
                    requestIdToken(fragment.getString(R.string.default_google_web_client_id))
                    requestEmail()
                }.build()
        val googleSignInClient = GoogleSignIn.getClient(fragment.activity as Activity, gso)
        val signInIntent = googleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, LoginType.GOOGLE.type)
    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailure(exception: Exception?): String {
        return when ((exception as ApiException).statusCode) {
            14 /*인터럽트*/ -> "인터럽트걸림"
            8 /*내부오류*/ -> "내부오류남"
            15 /*타임아웃*/ -> "타임아웃걸림"
            16 /*취소됨*/ -> "취소됨"
            18 /*클라이언트 사망*/ -> "클라쥬금"
            else -> "??뭔오류냐 // 코드 : " + exception.statusCode
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        when (requestCode) {
            LoginType.GOOGLE.type -> {
                Log.d("헤에헤이", "헤이헤이");
                val task: com.google.android.gms.tasks.Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: Exception) {

                    Log.e("구글 로그인", e.toString());
                }
            }
        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val auth = FirebaseAuth.getInstance()
        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("구글 로그인", "구글 로그인 credential : success");
                        context.startActivity(Intent(context, MainActivity::class.java))
                        val cUser = FirebaseAuth.getInstance().currentUser
                        userInfoStore(cUser!!.email!!, cUser.displayName!!, cUser.photoUrl.toString(), cUser.uid)


                    } else {
                        if (it.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(context, "이미 연동 및 가입된 이메일주소입니다.", Toast.LENGTH_SHORT).show()
                        }
                        Log.d("구글 로그인", "구글 로그인 credential : fail");
                    }
                })
    }

}
