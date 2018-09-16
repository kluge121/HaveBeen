package com.globe.havebeen.view.splash.presenter

import android.content.Context
import com.globe.havebeen.data.model.realm.City
import io.realm.Realm

/**
 * Created by baeminsu on 26/08/2018.
 */
class SplashPresenter(splashView: SplashContract.ISplashView)
    : SplashContract.ISplashPresenter {


    private val country = arrayOf("한국", "일본")
    private val koreanName = arrayOf("서울", "대전", "부산", "대구", "광주", "도쿄", "후쿠시마", "교토", "나가사키", "나라")

    override fun initCityList(context: Context) {

        val realm = Realm.getDefaultInstance()
        realm!!.beginTransaction()
        for (i in 0..9) {
            val city: City = realm.createObject(City::class.java)
            city.apply {
                if (i < 5) {
                    this.cityCountry = country[0]
                } else {
                    this.cityCountry = country[1]
                }
                this.cityKoreanName = koreanName[i]
                this.cityEnglishName = "Seoul"
            }
        }
        realm.commitTransaction()
    }


    init {
        splashView.presenter = this
    }

    override fun start() {

    }
}