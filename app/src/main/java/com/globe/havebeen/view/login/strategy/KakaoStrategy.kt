package com.globe.havebeen.view.login.strategy

import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import com.globe.havebeen.data.network.kakao.KakaoAuthApi
import com.google.android.gms.tasks.Task
import com.globe.havebeen.data.network.kakao.KakaoAuthService
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by baeminsu on 26/08/2018.
 */

class KakaoStrategy : ILoginStrategy {

    override fun login(fragment: Fragment) {
        Log.e("여기체크1", "췤췤")
        val kakaoSessionCallback = KakaoSessionCallback()
        Session.getCurrentSession().addCallback(kakaoSessionCallback)
        Log.e("여기체크2", "췤췤")

    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFailure(exception: Exception?): String {
        return ""
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


private fun getFirebaseJWT(kakaoUserProfile: MeV2Response): Task<String> {

    val source = TaskCompletionSource<String>()
    val validationObject: HashMap<String, String> = HashMap()

    validationObject.apply {
        put("userId", kakaoUserProfile.id.toString())
        if (kakaoUserProfile.kakaoAccount.hasEmail() == OptionalBoolean.TRUE) {
            Log.e("이메일체크1", kakaoUserProfile.kakaoAccount.hasEmail().toString())
            put("email", kakaoUserProfile.kakaoAccount.email)
        } else {
            Log.e("이메일체크2", kakaoUserProfile.kakaoAccount.hasEmail().toString())
            put("email", "")
        }
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

class KakaoSessionCallback : ISessionCallback {


    override fun onSessionOpened() {

        UserManagement.getInstance().me(object : MeV2ResponseCallback() {

            override fun onSuccess(result: MeV2Response?) {

                getFirebaseJWT(result!!).continueWithTask { p0 -> FirebaseAuth.getInstance().signInWithCustomToken(p0.result) }
                        .addOnCompleteListener{
                    if (it.isSuccessful) {
                        Log.e("깨독", "로그인 성공")
                    } else {
                        Log.e("깨독", "로그인 실패")
                    }
                }
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
