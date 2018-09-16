package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.globe.havebeen.R
import com.globe.havebeen.constants.CITY_UPDATE_DATE
import com.globe.havebeen.data.model.realm.City
import com.globe.havebeen.data.preferences.TraySharedPreference
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.room.create.adapter.CitySearchRecyclerViewAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.acitivity_city_search.*
import java.util.*

/**
// * Created by baeminsu on 10/09/2018.
// */
class RoomCitySearch : BaseActivity() {

    lateinit var adapter: CitySearchRecyclerViewAdapter
    lateinit var selectList: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_city_search)

        //테스트용
        val country = arrayOf("한국", "일본")
        val koreanName = arrayOf("서울", "대전", "부산", "대구", "광주", "도쿄", "후쿠시마", "교토", "나가사키", "나라")
        val nation = arrayOf("korea", "usa", "japan", "korea", "usa", "korea", "france", "german", "england", "korea")

        selectList = intent.getIntegerArrayListExtra("selectList") as ArrayList<Int>
        adapter = CitySearchRecyclerViewAdapter(this, selectList)

        if (TraySharedPreference(this).getString(CITY_UPDATE_DATE, "0") == "0") {
            val realm = Realm.getDefaultInstance()

            realm!!.beginTransaction()
            for (i in 0..9) {
                var id: Number? = realm.where(City::class.java).max("id")
                if (id == null) {
                    id = 1
                } else {
                    id = id.toInt() + 1
                }

                val city: City = realm.createObject(City::class.java, id)


                city.apply {
                    if (i < 5) {
                        this.cityCountry = country[0]
                    } else {
                        this.cityCountry = country[1]
                    }
                    this.cityKoreanName = koreanName[i]
                    this.cityEnglishName = nation[i]
                }
            }
            var id2: Number? = realm.where(City::class.java).max("id")
            if (id2 == null) {
                id2 = 1
            } else {
                id2 = id2.toInt() + 1
            }
            val city2 = realm.createObject(City::class.java, id2)
            city2.cityEnglishName = "Popular City"
            city2.cityKoreanName = "인기 도시"
            city2.cityCountry = "인기 국가"
            realm.commitTransaction()
            TraySharedPreference(this).put(CITY_UPDATE_DATE, "1")
        }

        citySearchRecyclerview.adapter = adapter
        citySearchRecyclerview.layoutManager = LinearLayoutManager(this)
        adapter.defaultClear()



        citySearchBackBtn.setOnClickListener {
            finish()
        }
        citySearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val text = citySearchEditText.text.toString().toLowerCase(Locale.getDefault())
                adapter.filter(text)

                if (!p0.isNullOrBlank()) {
                    citySearchTv.visibility = View.INVISIBLE
                } else {
                    citySearchTv.visibility = View.VISIBLE
                    adapter.defaultClear()
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

    }
}