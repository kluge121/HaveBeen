package com.globe.havebeen.extension

import org.joda.time.DateTime
import org.joda.time.Days
import java.util.*

/**
 * Created by baeminsu on 09/09/2018.
 */

fun dayIntToKoreaDay(day: Int): String {
    return when (day) {
        1 -> "일요일"
        2 -> "월요일"
        3 -> "화요일"
        4 -> "수요일"
        5 -> "목요일"
        6 -> "금요일"
        7 -> "토요일"
        else -> {
            ""
        }
    }
}


fun calendarBetweenDay(startDate: Calendar, endDate: Calendar): Int {

    val startMillis = startDate.timeInMillis
    val startDateTime = DateTime(startMillis)
    val stopMillis = endDate.timeInMillis
    val stopDateTime = DateTime(stopMillis)
    val betweenDay = Days.daysBetween(startDateTime, stopDateTime).days

    return betweenDay

}


//String 첫글자 초성 추출
fun String.getInitialSound(): Char? {
    val chs = arrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
    if (this.isNotEmpty()) {
        val firstChar = this[0]
        if (firstChar.toInt() >= 0xAC00) {
            val uniVal = firstChar.toInt() - 0xAC00
            val cho = (uniVal - uniVal % 28) / 28 / 21
            return chs[cho]
        }
    }
    return null
}

