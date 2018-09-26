package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.extension.calendarBetweenDay
import com.globe.havebeen.extension.dayIntToKoreaDay
import com.globe.havebeen.view.room.create.custom.CustomDateRangeCalendarView
import com.globe.havebeen.view.room.create.custom.CustomSnackbar
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import kotlinx.android.synthetic.main.fragment_room_create_plan.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DAY_OF_WEEK
import java.time.temporal.TemporalQueries.zone
import org.joda.time.DateTime
import org.joda.time.Days


/**
 * Created by baeminsu on 06/09/2018.
 */
class RoomCreatePlanFragment : Fragment(), RoomCreateContract.IRoomCreatePlanView {


    lateinit var createRoomCalendar: CustomDateRangeCalendarView
    var snackbar: CustomSnackbar? = null
    lateinit var startDate: Calendar
    lateinit var endDate: Calendar
    var snackbarFlag: Boolean = false


    companion object {
        fun newInstance() = RoomCreatePlanFragment()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && context != null) {

            //스킵버튼 온오프는 캘린더 콜백에서 -> line 110
            if ((context as RoomCreateActivity).roomCreateInfo.startDate != null
                    && (context as RoomCreateActivity).roomCreateInfo.endDate != null) {
                startDate = (context as RoomCreateActivity).roomCreateInfo.startDate!!
                endDate = (context as RoomCreateActivity).roomCreateInfo.endDate!!
                snackbar!!.setText(planMakeText(startDate, endDate))
                createRoomCalendar.setSelectedDateRange(startDate, endDate)
                snackbar!!.show()
            } else {
                snackbar!!.dismiss()
            }
        } else if (snackbar != null && context != null) {
            snackbar!!.dismiss()
        }
    }

    override lateinit var presenter: RoomCreateContract.IRoomCreatePlanPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_room_create_plan, container, false)



        with(view) {

            createRoomCalendar = findViewById(R.id.createRoomCalendar)
            createRoomCalendar.setFragmentForTvReset(this@RoomCreatePlanFragment)
            snackbar = CustomSnackbar.make(activity!!.findViewById(android.R.id.content), Snackbar.LENGTH_INDEFINITE)

            snackbar!!.setAction("일정 추가", View.OnClickListener {
                (context as RoomCreateActivity).roomCreateInfo.startDate = startDate
                (context as RoomCreateActivity).roomCreateInfo.endDate = endDate
                (context as RoomCreateActivity).onNextPressed()
            })



            createRoomCalendar.setCalendarListener(object : CustomDateRangeCalendarView.CalendarListener {
                override fun onTextViewReset() {
                    createRoomPlanStartTv.visibility = View.INVISIBLE
                    createRoomPlanEndTv.visibility = View.INVISIBLE
                    createRoomPlanStartDayTv.visibility = View.INVISIBLE
                    createRoomPlanEndDayTv.visibility = View.INVISIBLE
                }


                override fun onFirstDateSelected(startDate: Calendar) {
                    (activity as RoomCreateActivity).skipBtnHide(false)
                    val sdf = SimpleDateFormat("MM월  dd일")
                    createRoomPlanStartTv.visibility = View.VISIBLE
                    createRoomPlanStartDayTv.visibility = View.VISIBLE
                    createRoomPlanArrow.visibility = View.INVISIBLE

                    val startText = "${sdf.format(startDate.time)}\n${dayIntToKoreaDay(startDate.get(DAY_OF_WEEK))}"
                    createRoomPlanStartDayTv.text = startText
                    snackbarFlag = false
                    snackbar!!.dismiss()

                }

                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    (activity as RoomCreateActivity).skipBtnHide(true)
                    snackbarFlag = true
                    this@RoomCreatePlanFragment.startDate = startDate
                    this@RoomCreatePlanFragment.endDate = endDate

                    createRoomPlanEndTv.visibility = View.VISIBLE
                    createRoomPlanEndDayTv.visibility = View.VISIBLE

                    val sdf = SimpleDateFormat("MM월  dd일")
                    val startText = "${sdf.format(startDate.time)}\n${dayIntToKoreaDay(startDate.get(DAY_OF_WEEK))}"
                    val endText = "${sdf.format(endDate.time)}\n${dayIntToKoreaDay(endDate.get(DAY_OF_WEEK))}"
                    createRoomPlanStartDayTv.text = startText
                    createRoomPlanEndDayTv.text = endText

                    val betweenDay = calendarBetweenDay(startDate, endDate)

                    createRoomPlanArrow.visibility = View.VISIBLE
                    (activity as RoomCreateActivity).tripDayPeriod = betweenDay

                    snackbar!!.setText(planMakeText(startDate, endDate))
                    snackbar!!.show()
                }
            })
            createRoomCalendar.resetAllSelectedViews()
        }
        return view
    }

    override fun planMakeText(startDate: Calendar, endDate: Calendar): String {
        val betweenDay = calendarBetweenDay(startDate, endDate)
        return "${betweenDay}박 ${betweenDay + 1}일 / 확정"
    }
}