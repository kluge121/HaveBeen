package com.globe.havebeen.view.room.create.custom

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.archit.calendardaterangepicker.customviews.DateRangeMonthView
import com.archit.calendardaterangepicker.manager.DateRangeCalendarManager
import com.archit.calendardaterangepicker.models.CalendarStyleAttr
import com.globe.havebeen.R
import com.globe.havebeen.view.room.create.RoomCreatePlanFragment
import java.util.*

/**
 * Created by baeminsu on 07/09/2018.
 */

class CustomAdapterEventCalendarMonths : PagerAdapter {


    private var mContext: Context? = null
    private var dataList = ArrayList<Calendar>()
    private var calendarStyleAttr: CalendarStyleAttr? = null
    private var calendarListener: CustomDateRangeCalendarView.CalendarListener? = null
    private var dateRangeCalendarManager: DateRangeCalendarManager? = null
    private var mHandler: Handler? = null


    constructor(context: Context, list: List<Calendar>, calendarStyleAttr: CalendarStyleAttr) {
        this.mContext = context
        dataList = list as ArrayList<Calendar>
        this.calendarStyleAttr = calendarStyleAttr
        dateRangeCalendarManager = DateRangeCalendarManager()
        mHandler = Handler()
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val modelObject = dataList[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.layout_custom_calendar_pager_month, container, false) as ViewGroup

        val dateRangeMonthView = layout.findViewById<CustomDateRangeMonthView>(R.id.cvEventCalendarView)

        dateRangeMonthView.drawCalendarForMonth(this.calendarStyleAttr!!, getCurrentMonth(modelObject), this.dateRangeCalendarManager!!)
        dateRangeMonthView.setCalendarListener(calendarAdapterListener)

        container.addView(layout)
        return layout
    }

    /**
     * To clone calendar obj and get current month calendar starting from 1st date.
     *
     * @param calendar - Calendar
     * @return - Modified calendar obj of month of 1st date.
     */
    private fun getCurrentMonth(calendar: Calendar): Calendar {
        val current = calendar.clone() as Calendar
        current.set(Calendar.DAY_OF_MONTH, 1)
        return current
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


    private val calendarAdapterListener = object : CustomDateRangeCalendarView.CalendarListener {

        override fun onTextViewReset() {

            mHandler!!.postDelayed(Runnable { notifyDataSetChanged() }, 50)
            if (calendarListener != null) {
                calendarListener!!.onTextViewReset()
            }
        }

        override fun onFirstDateSelected(startDate: Calendar) {

            mHandler!!.postDelayed(Runnable { notifyDataSetChanged() }, 50)


            if (calendarListener != null) {
                calendarListener!!.onFirstDateSelected(startDate)
            }
        }

        override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
            mHandler!!.postDelayed(Runnable { notifyDataSetChanged() }, 50)
            if (calendarListener != null) {
                calendarListener!!.onDateRangeSelected(startDate, endDate)
            }
        }
    }

    fun setCalendarListener(calendarListener: CustomDateRangeCalendarView.CalendarListener) {
        this.calendarListener = calendarListener
    }

    /**
     * To redraw calendar.
     */
    fun invalidateCalendar() {
        mHandler!!.postDelayed(Runnable { notifyDataSetChanged() }, 50)
    }

    /**
     * To remove all selection and redraw current calendar
     */
    fun resetAllSelectedViews() {
        dateRangeCalendarManager!!.setMinSelectedDate(null)
        dateRangeCalendarManager!!.setMaxSelectedDate(null)
        notifyDataSetChanged()

    }


    fun setSelectedDate(minSelectedDate: Calendar, maxSelectedDate: Calendar) {
        dateRangeCalendarManager!!.setMinSelectedDate(minSelectedDate)
        dateRangeCalendarManager!!.setMaxSelectedDate(maxSelectedDate)
        notifyDataSetChanged()
    }

    fun getMinSelectedDate(): Calendar {
        return dateRangeCalendarManager!!.getMinSelectedDate()
    }

    fun getMaxSelectedDate(): Calendar {
        return dateRangeCalendarManager!!.getMaxSelectedDate()
    }

}