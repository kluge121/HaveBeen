package com.globe.havebeen.view.room.create.custom

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.archit.calendardaterangepicker.customviews.CustomTextView
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.archit.calendardaterangepicker.models.CalendarStyleAttr
import com.globe.havebeen.R
import com.globe.havebeen.view.room.create.RoomCreatePlanFragment
import java.text.DateFormatSymbols
import java.util.*


/**
 * Created by baeminsu on 07/09/2018.
 */
class CustomDateRangeCalendarView : LinearLayout {

    interface CalendarListener {
        fun onTextViewReset()

        fun onFirstDateSelected(startDate: Calendar)

        fun onDateRangeSelected(startDate: Calendar, endDate: Calendar)
    }

    private lateinit var tvYearTitle: CustomTextView
    private lateinit var imgVNavLeft: AppCompatImageView
    private lateinit var imgVNavRight: AppCompatImageView
    private lateinit var calendarStyleAttr: CalendarStyleAttr
    private lateinit var vpCalendar: ViewPager
    private val dataList = ArrayList<Calendar>()
    private lateinit var adapterEventCalendarMonths: CustomAdapterEventCalendarMonths
    private lateinit var fragment: RoomCreatePlanFragment

    private val TOTAL_ALLOWED_MONTHS = 30
    private lateinit var locale: Locale

    constructor(context: Context) : super(context) {
        customInitView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        customInitView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        customInitView(context, attrs)
    }


    fun setFragmentForTvReset(fragment: RoomCreatePlanFragment) {
        this.fragment = fragment
    }


    private fun customInitView(context: Context, attrs: AttributeSet?) {
        calendarStyleAttr = CalendarStyleAttr(context, attrs)

        locale = context.resources.configuration.locale
        LayoutInflater.from(context).inflate(R.layout.layout_custom_calendar_container, this, true)

        val rlHeaderCalendar = findViewById<RelativeLayout>(R.id.rlHeaderCalendar)
        rlHeaderCalendar.background = calendarStyleAttr.headerBg

        tvYearTitle = findViewById(R.id.tvYearTitle)
        imgVNavLeft = findViewById(R.id.imgVNavLeft)
        imgVNavRight = findViewById(R.id.imgVNavRight)
        vpCalendar = findViewById(R.id.vpCalendar)

        dataList.clear()

        val today = Calendar.getInstance().clone() as Calendar
        today.add(Calendar.MONTH, -TOTAL_ALLOWED_MONTHS)


        for (i in 0 until TOTAL_ALLOWED_MONTHS * 2) {
            dataList.add(today.clone() as Calendar)
            today.add(Calendar.MONTH, 1)
        }
        adapterEventCalendarMonths = CustomAdapterEventCalendarMonths(context, dataList, calendarStyleAttr)
        vpCalendar.adapter = adapterEventCalendarMonths
        vpCalendar.offscreenPageLimit = 0
        vpCalendar.currentItem = TOTAL_ALLOWED_MONTHS
        setCalendarYearTitle(TOTAL_ALLOWED_MONTHS)

        setListeners()
    }

    private fun setCalendarYearTitle(position: Int) {

        val currentCalendarMonth = dataList[position]
        var dateText = DateFormatSymbols(locale).months[currentCalendarMonth.get(Calendar.MONTH)]
        dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length)


        val yearTitle = "${currentCalendarMonth.get(Calendar.YEAR)}년 $dateText"

        tvYearTitle.text = yearTitle
        tvYearTitle.setTextColor(calendarStyleAttr.titleColor)

    }

    private fun setListeners() {

        vpCalendar.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                setCalendarYearTitle(position)
                setNavigationHeader(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        imgVNavLeft.setOnClickListener {
            val newPosition = vpCalendar.currentItem - 1
            if (newPosition > -1) {
                vpCalendar.currentItem = newPosition
            }
        }
        imgVNavRight.setOnClickListener {
            val newPosition = vpCalendar.currentItem + 1
            if (newPosition < dataList.size) {
                vpCalendar.currentItem = newPosition
            }
        }
    }

    private fun setNavigationHeader(position: Int) {
        imgVNavRight.visibility = View.VISIBLE
        imgVNavLeft.visibility = View.VISIBLE
        if (position == 0) {
            imgVNavLeft.visibility = View.INVISIBLE
        } else if (position == dataList.size - 1) {
            imgVNavRight.visibility = View.INVISIBLE
        }
    }

    fun setCalendarListener(calendarListener: CalendarListener) {
        adapterEventCalendarMonths.setCalendarListener(calendarListener)
    }

    /**
     * To apply custom fonts to all the text views
     *
     * @param fonts - Typeface that you want to apply
     */
    fun setFonts(fonts: Typeface) {
        tvYearTitle.typeface = fonts
        calendarStyleAttr.fonts = fonts
        adapterEventCalendarMonths.invalidateCalendar()
    }

    /**
     * To remove all selection and redraw current calendar
     */
    fun resetAllSelectedViews() {
        adapterEventCalendarMonths.resetAllSelectedViews()

    }

    /**
     * To set week offset. To start week from any of the day. Default is 0 (Sunday).
     *
     * @param offset 0-Sun, 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat
     */
    fun setWeekOffset(offset: Int) {
        calendarStyleAttr.weekOffset = offset
        adapterEventCalendarMonths.invalidateCalendar()
    }

    /**
     * To set left navigation ImageView drawable
     */
    fun setNavLeftImage(leftDrawable: Drawable) {
        imgVNavLeft.setImageDrawable(leftDrawable)
    }

    /**
     * To set right navigation ImageView drawable
     */
    fun setNavRightImage(rightDrawable: Drawable) {
        imgVNavRight.setImageDrawable(rightDrawable)
    }

    fun setSelectedDateRange(startDate: Calendar?, endDate: Calendar?) {
        if (startDate == null && endDate != null) {
            throw RuntimeException("Start date can not be null if you are setting end date.")
        } else if (endDate != null && endDate.before(startDate)) {
            throw RuntimeException("Start date can not be after end date.")
        }
        if (startDate != null) {
            if (endDate != null) {
                adapterEventCalendarMonths.setSelectedDate(startDate, endDate)
            }
        }
    }

}
