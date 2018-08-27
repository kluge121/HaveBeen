package com.globe.havebeen.config

/**
 * Created by baeminsu on 25/08/2018.
 */
enum class LoginType(var type: Int) {
    GOOGLE(10), FACEBOOK(64206), KAKAO(30), LOCAL(40);
    fun getValue(): Int = type
}

