package com.globe.havebeen.view.login.strategy

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.globe.havebeen.data.network.kakao.KakaoAuthApi
import com.globe.havebeen.data.network.kakao.KakaoAuthService
import com.globe.havebeen.view.main.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by baeminsu on 26/08/2018.
 */

class KakaoStrategy : ILoginStrategy {


    override fun login(fragment: Fragment) {
        val kakaoSessionCallback = KakaoSessionCallback(fragment.context!!)
        Session.getCurrentSession().addCallback(kakaoSessionCallback)

    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailure(exception: Exception?): String {
        return ""
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }

    private fun getFirebaseJWT(kakaoUserProfile: MeV2Response, context: Context): Task<String> {

        val source = TaskCompletionSource<String>()
        val validationObject: HashMap<String, String> = HashMap()

        validationObject.apply {
            put("userId", kakaoUserProfile.id.toString())
            put("email", kakaoUserProfile.kakaoAccount.email)
            put("nickname", kakaoUserProfile.nickname)
            put("profileImage", kakaoUserProfile.thumbnailImagePath)
        }
        val jsonData: String = GsonBuilder()
                .setLenient()
                .create().toJson(validationObject)

        val kakaoAuthApi: KakaoAuthApi = KakaoAuthService.getKakaoLoginClient().create(KakaoAuthApi::class.java)
        kakaoAuthApi.getFirebaseToken(jsonData).enqueue(object : Callback<FirebaseAuthToken> {
            override fun onResponse(call: Call<FirebaseAuthToken>?, response: Response<FirebaseAuthToken>?) {
                if (response?.isSuccessful!!) {
                    val firebaseAuthToken: FirebaseAuthToken = response.body()!!
                    source.setResult(firebaseAuthToken.firebaseToken)
                } else {
                    Log.e("카카오 로그인 세션 리스폰", response.errorBody()!!.charStream().readText())
                }

            }

            override fun onFailure(call: Call<FirebaseAuthToken>?, t: Throwable?) {
                Log.e("카카오 로그인 세션 실패", "카카오 세션 콜백 실패")
            }
        })
        return source.task
    }

    inner class KakaoSessionCallback(val context: Context) : ISessionCallback {


        override fun onSessionOpened() {

            UserManagement.getInstance().me(object : MeV2ResponseCallback() {

                override fun onSuccess(result: MeV2Response?) {

                    getFirebaseJWT(result!!, context).continueWithTask { p0 -> FirebaseAuth.getInstance().signInWithCustomToken(p0.result) }
                            .addOnCompleteListener(OnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.e("깨독1", "로그인 성공")
                                    val cUser = FirebaseAuth.getInstance().currentUser
                                    userInfoStore(cUser!!.email!!, cUser.displayName!!, cUser.photoUrl.toString(), cUser.uid)
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                    (context as Activity).finish()
                                } else {
                                    if (it.exception is FirebaseAuthUserCollisionException) {
                                        Toast.makeText(context, "이미 연동 및 가입된 이메일주소입니다.", Toast.LENGTH_SHORT).show()
                                    }
                                    Log.e("깨독1", "로그인 실패")
                                }
                            })
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Log.e("깨독", "로그인 세션 클로즈 ${errorResult?.errorMessage}")
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {


        }
    }

    class FirebaseAuthToken {
        @SerializedName("firebase_token")
        val firebaseToken: String? = null
    }

}



