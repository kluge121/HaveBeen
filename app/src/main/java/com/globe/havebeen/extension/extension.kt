package com.globe.havebeen.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import org.joda.time.DateTime
import org.joda.time.Days
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.y
import android.R.attr.x
import android.graphics.Point
import android.graphics.Rect
import android.view.Display



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

fun Calendar.convertKoreanDate(): String {
    val format = SimpleDateFormat("yyyy년 MM월 dd")
    val dayNum = this.get(Calendar.DAY_OF_WEEK)
    val dateString = format.format(this.time)
    val days: String
    days = dayIntToKoreaDay(dayNum)
    return dateString.plus(days)
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

    val han = arrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
    val alp = getAlphabetCharArray()



    if (this.isNotEmpty()) {
        var firstChar = this[0]
        if (firstChar.toInt() >= 0xAC00) {
            val uniVal = firstChar.toInt() - 0xAC00
            val cho = (uniVal - uniVal % 28) / 28 / 21
            return han[cho]
        } else if (firstChar.toInt() in 0X0021..0X007A) {

            if (firstChar.isLowerCase()) {
                firstChar = firstChar.toUpperCase()
            }
            val init = firstChar.toUpperCase().toInt()

            if (alp.contains(init.toChar())) {
                return alp[alp.indexOf((init.toChar()))]
            }
        }
    }
    return null
}

fun getAlphabetCharArray(): ArrayList<Char> {
    val alp = ArrayList<Char>()
    for (i in 65..90) {
        alp.add(i.toChar())
    }
    for (i in 48..57) {
        alp.add(i.toChar())
    }
    for (i in 33..47) {
        alp.add(i.toChar())
    }
    for (i in 58..64) {
        alp.add(i.toChar())
    }
    for (i in 91..96) {
        alp.add(i.toChar())
    }
    for (i in 123..126) {
        alp.add(i.toChar())
    }
    return alp
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            // set once again checked value, so view will be updated
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
//        Log.e(TAG, "Unable to get shift mode field", e)
    } catch (e: IllegalStateException) {
//        Log.e(TAG, "Unable to change value of shift mode", e)
    }
}


fun takeScreenShot(activity: Activity): Bitmap {
    val view = activity.window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()


    val b1 = view.drawingCache
    val frame = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top

    val display = activity.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val width = size.x
    val height = size.y


    val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
    view.destroyDrawingCache()
    return b

}




