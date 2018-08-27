package com.globe.havebeen

import android.app.Application
import com.kakao.auth.*
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by baeminsu on 25/08/2018.
 */
class HaveBeenApplication : Application() {

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

        FlowManager.init(this)
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V)

        KakaoSDK.init(KakaoSDKAdapter())
    }


    companion object {
        lateinit var instance: HaveBeenApplication private set
    }
}