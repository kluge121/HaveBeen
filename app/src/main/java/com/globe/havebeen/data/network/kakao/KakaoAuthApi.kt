package com.globe.havebeen.data.network.kakao

import com.globe.havebeen.view.login.strategy.FirebaseAuthToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface KakaoAuthApi {


    @Headers("Content-Type: application/json")
    @POST("/verifyKakao")
    fun getFirebaseToken(@Body body: String): Call<FirebaseAuthToken>

}
