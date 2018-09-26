package com.globe.havebeen.view.room.create.dialog

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Gravity
import android.view.Window
import com.globe.havebeen.R
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import com.globe.havebeen.data.model.RoomCreateInfo
import com.globe.havebeen.extension.calendarBetweenDay
import com.globe.havebeen.extension.convertKoreanDate
import kotlinx.android.synthetic.main.dialog_create_room.*
import java.text.DecimalFormat


/**
 * Created by baeminsu on 21/09/2018.
 */


class RoomCreateDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.CENTER)
        setContentView(R.layout.dialog_create_room)
        val info = intent.getSerializableExtra("info") as RoomCreateInfo
        setInfo(info)

        roomCreateDialogCloseBtn.setOnClickListener {
            finish()
        }

        roomCreateDialogSummit.setOnClickListener {

        }

    }

    private fun setInfo(roomCreateInfo: RoomCreateInfo) {

        var date: String
        var city = ""
        var user = ""
        var cost: String

        //날짜 세팅
        if (roomCreateInfo.startDate != null) {
            date = "${roomCreateInfo.startDate!!.convertKoreanDate()} - ${roomCreateInfo.endDate!!.convertKoreanDate()}"
            val between = calendarBetweenDay(roomCreateInfo.startDate!!, roomCreateInfo.endDate!!)
            date = date.plus("<br><strong>").plus("${between}박 ${between + 1}일").plus("</strong>")
            roomCreateDialogScheduleTv.text = date

        } else {

            date = "일정이 결정되지 않았습니다."
        }

        //도시 세팅
        if (roomCreateInfo.cityList.size >= 1) {
            if (roomCreateInfo.cityList.size > 1)
                city = "<strong>${roomCreateInfo.thumCityName}</strong> 외 ${roomCreateInfo.cityList.size - 1}곳"
            else if (roomCreateInfo.cityList.size == 1)
                city = "<strong>${roomCreateInfo.thumCityName}</strong>"
            roomCreateDialogPlaceIv.setImageResource(R.drawable.room_create_dialog_place)

        } else {
            roomCreateDialogPlaceIv.setImageResource(R.drawable.room_create_dialog_gray)
            city = "미정"
        }

        //유저 세팅
        if (roomCreateInfo.friendList.size >= 1) {
            if (roomCreateInfo.friendList.size > 1)
                user = "<strong>${roomCreateInfo.friendList[0].name}님</strong><br>외 ${roomCreateInfo.friendList.size - 1}명"
            else if (roomCreateInfo.friendList.size == 1)
                user = "<strong>${roomCreateInfo.friendList[0].name}</strong>"
            roomCreateDialogUserIv.setImageResource(R.drawable.room_create_dialog_user)

        } else {
            roomCreateDialogUserIv.setImageResource(R.drawable.room_create_dialog_user_gray)
            user = "미정"
        }

        if (roomCreateInfo.cost != null) {
            roomCreateDialogMoneyIv.setImageResource(R.drawable.room_create_dialog_money)
            val decimalFormat = DecimalFormat("#,###")
            val convertCost = decimalFormat.format(roomCreateInfo.cost)
            cost = "총 여행 에산<br><strong>$convertCost</strong>원"
        } else {
            roomCreateDialogMoneyIv.setImageResource(R.drawable.room_create_dialog_money_gray)
            cost = "미정"
        }

        roomCreateDialogScheduleTv.text = Html.fromHtml(date)
        roomCreateDialogPlaceTv.text = Html.fromHtml(city)
        roomCreateDialogUserTv.text = Html.fromHtml(user)
        roomCreateDialogMoneyTv.text = Html.fromHtml(cost)

    }
}