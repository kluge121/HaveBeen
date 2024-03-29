package com.globe.havebeen.data.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by baeminsu on 10/09/2018.
 */


public open class City : RealmObject() {

    @PrimaryKey
    public open var id: Int? = null
    public open var cityKoreanName: String? = null
    public open var cityEnglishName: String? = null
    public open var cityCountry: String? = null
}