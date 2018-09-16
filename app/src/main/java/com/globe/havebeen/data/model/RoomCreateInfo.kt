package com.globe.havebeen.data.model

import com.globe.havebeen.data.model.realm.City
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by baeminsu on 14/09/2018.
 */


data class RoomCreateInfo(

        //일정정보
        var startDate: Calendar? = null,
        var endDate: Calendar? = null,

        //도시정보
        var cityList: ArrayList<City>? = null,

        //경비정보
        var cost: Int? = null,

        //친구리스트
        var friendList: ArrayList<User>? = null


) {

}