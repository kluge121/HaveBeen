package com.globe.havebeen

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.globe.havebeen.data.preferences.TraySharedPreference
import com.kakao.auth.*

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by baeminsu on 25/08/2018.
 */
class HaveBeenApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }

    private class KakaoSDKAdapter : KakaoAdapter() {
        //kakao
        override fun getApplicationConfig() = IApplicationConfig { instance }

        override fun getSessionConfig() = object : ISessionConfig {
            override fun isSaveFormData() = false

            override fun getAuthTypes() = arrayOf(AuthType.KAKAO_LOGIN_ALL)

            override fun isSecureMode() = false

            override fun getApprovalType() = ApprovalType.INDIVIDUAL

            override fun isUsingWebviewTimer() = true
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
        KakaoSDK.init(KakaoSDKAdapter())

    }


    companion object {
        lateinit var instance: HaveBeenApplication private set
    }
}