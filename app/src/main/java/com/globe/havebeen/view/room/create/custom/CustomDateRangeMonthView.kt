package com.globe.havebeen.view.room.create.custom

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.archit.calendardaterangepicker.R
import com.archit.calendardaterangepicker.customviews.CustomTextView
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.archit.calendardaterangepicker.manager.DateRangeCalendarManager
import com.archit.calendardaterangepicker.models.CalendarStyleAttr
import com.archit.calendardaterangepicker.models.DayContainer
import com.archit.calendardaterangepicker.timepicker.AwesomeTimePickerDialog
import com.globe.havebeen.view.room.create.RoomCreatePlanFragment
import java.text.ParseException
import java.util.*

/**
 * Created by baeminsu on 07/09/2018.
 */
class CustomDateRangeMonthView : LinearLayout {

    private val LOG_TAG = com.archit.calendardaterangepicker.customviews.DateRangeMonthView::class.java.simpleName
    private var llDaysContainer: LinearLayout? = null
    private var llTitleWeekContainer: LinearLayout? = null

    private var currentCalendarMonth: Calendar? = null

    private var calendarStyleAttr: CalendarStyleAttr? = null

    private var calendarListener: CustomDateRangeCalendarView.CalendarListener? = null

    private var dateRangeCalendarManager: DateRangeCalendarManager? = null

    private val FILTER_MODE = PorterDuff.Mode.SRC_IN

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initView(context, attributeSet)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        initView(context, attributeSet)
    }


    fun setCalendarListener(calendarListener: CustomDateRangeCalendarView.CalendarListener) {
        this.calendarListener = calendarListener
    }


    /**
     * To initialize child views
     *
     * @param context      - App context
     * @param attributeSet - Attr set
     */
    private fun initView(context: Context, attributeSet: AttributeSet?) {
        val layoutInflater = LayoutInflater.from(context)
        val mainView = layoutInflater.inflate(com.globe.havebeen.R.layout.layout_custom_calendar_month, this, true) as LinearLayout
        llDaysContainer = mainView.findViewById(R.id.llDaysContainer)
        llTitleWeekContainer = mainView.findViewById(R.id.llTitleWeekContainer)

        setListeners()

        if (isInEditMode) {
            return
        }

    }


    /**
     * To set listeners.
     */
    private fun setListeners() {


    }

    private val dayClickListener = OnClickListener { view ->
        val key = view.tag as Int
        val selectedCal = Calendar.getInstance()
        var date = Date()
        try {
            date = DateRangeCalendarManager.SIMPLE_DATE_FORMAT.parse(key.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        selectedCal.time = date

        var minSelectedDate: Calendar? = dateRangeCalendarManager!!.minSelectedDate
        var maxSelectedDate: Calendar? = dateRangeCalendarManager!!.maxSelectedDate

        if (minSelectedDate != null && maxSelectedDate == null) {
            maxSelectedDate = selectedCal

            val startDateKey = DayContainer.GetContainerKey(minSelectedDate)
            val lastDateKey = DayContainer.GetContainerKey(maxSelectedDate!!)

            if (startDateKey == lastDateKey) {
                minSelectedDate = maxSelectedDate
            } else if (startDateKey > lastDateKey) {
                val temp = minSelectedDate.clone() as Calendar
                minSelectedDate = maxSelectedDate
                maxSelectedDate = temp
            }
        } else if (maxSelectedDate == null) {
            //This will call one time only
            minSelectedDate = selectedCal
        } else {
            calendarListener!!.onTextViewReset()
            minSelectedDate = selectedCal
            maxSelectedDate = null
        }

        dateRangeCalendarManager!!.minSelectedDate = minSelectedDate
        dateRangeCalendarManager!!.maxSelectedDate = maxSelectedDate
        drawCalendarForMonth(currentCalendarMonth)

        if (calendarStyleAttr!!.isShouldEnabledTime) {
            val finalMinSelectedDate = minSelectedDate
            val finalMaxSelectedDate = maxSelectedDate
            val awesomeTimePickerDialog = AwesomeTimePickerDialog(context, context.getString(R.string.select_time), object : AwesomeTimePickerDialog.TimePickerCallback {
                override fun onTimeSelected(hours: Int, mins: Int) {
                    selectedCal.set(Calendar.HOUR, hours)
                    selectedCal.set(Calendar.MINUTE, mins)


                    if (calendarListener != null) {

                        if (finalMaxSelectedDate != null) {
                            calendarListener!!.onDateRangeSelected(finalMinSelectedDate!!, finalMaxSelectedDate)
                        } else {
                            calendarListener!!.onFirstDateSelected(finalMinSelectedDate!!)
                        }
                    }
                }

                override fun onCancel() {
                    this@CustomDateRangeMonthView.resetAllSelectedViews()
                }
            })
            awesomeTimePickerDialog.showDialog()
        } else {
            Log.i(LOG_TAG, "Time: " + selectedCal.time.toString())
            if (maxSelectedDate != null) {
                calendarListener!!.onDateRangeSelected(minSelectedDate!!, maxSelectedDate)
            } else {
                calendarListener!!.onFirstDateSelected(minSelectedDate!!)
            }
        }
    }

    /**
     * To draw calendar for the given month. Here calendar object should start from date of 1st.
     *
     * @param calendarStyleAttr        Calendar style attributes
     * @param month                    Month to be drawn
     * @param dateRangeCalendarManager Calendar data manager
     */
    fun drawCalendarForMonth(calendarStyleAttr: CalendarStyleAttr, month: Calendar, dateRangeCalendarManager: DateRangeCalendarManager) {
        this.calendarStyleAttr = calendarStyleAttr
        this.currentCalendarMonth = month.clone() as Calendar
        this.dateRangeCalendarManager = dateRangeCalendarManager
        setFonts()
        setWeekTitleColor(calendarStyleAttr.weekColor)
        drawCalendarForMonth(currentCalendarMonth)
    }

    /**
     * To draw calendar for the given month. Here calendar object should start from date of 1st.
     *
     * @param month Calendar month
     */
    private fun drawCalendarForMonth(month: Calendar?) {

        currentCalendarMonth = month!!.clone() as Calendar
        currentCalendarMonth!!.set(Calendar.DATE, 1)
        currentCalendarMonth!!.set(Calendar.HOUR, 0)
        currentCalendarMonth!!.set(Calendar.MINUTE, 0)
        currentCalendarMonth!!.set(Calendar.SECOND, 0)

        val weekTitle = context.resources.getStringArray(R.array.week_sun_sat)

        //To set week day title as per offset
        for (i in 0..6) {

            val textView = llTitleWeekContainer!!.getChildAt(i) as CustomTextView

            val weekStr = weekTitle[(i + calendarStyleAttr!!.weekOffset) % 7]
            textView.text = weekStr

        }

        var startDay = month.get(Calendar.DAY_OF_WEEK) - calendarStyleAttr!!.weekOffset

        //To ratate week day according to offset
        if (startDay < 1) {
            startDay = startDay + 7
        }

        month.add(Calendar.DATE, -startDay + 1)

        for (i in 0 until llDaysContainer!!.childCount) {
            val weekRow = llDaysContainer!!.getChildAt(i) as LinearLayout

            for (j in 0..6) {
                val rlDayContainer = weekRow.getChildAt(j) as RelativeLayout

                val container = DayContainer(rlDayContainer)

                container.tvDate.text = month.get(Calendar.DATE).toString()
                if (calendarStyleAttr!!.fonts != null) {
                    container.tvDate.typeface = calendarStyleAttr!!.fonts
                }
                drawDayContainer(container, month)
                month.add(Calendar.DATE, 1)
            }
        }
    }

    /**
     * To draw specific date container according to past date, today, selected or from range.
     *
     * @param container - Date container
     * @param calendar  - Calendar obj of specific date of the month.
     */
    private fun drawDayContainer(container: DayContainer, calendar: Calendar) {

        val today = Calendar.getInstance()

        val date = calendar.get(Calendar.DATE)


        if (currentCalendarMonth!!.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
            hideDayContainer(container)
        } else if (today.after(calendar) && today.get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR)) {
            disableDayContainer(container)
            container.tvDate.text = date.toString()
        } else {

            @DateRangeCalendarManager.RANGE_TYPE
            val type = dateRangeCalendarManager!!.checkDateRange(calendar)
            if (type == DateRangeCalendarManager.RANGE_TYPE.START_DATE || type == DateRangeCalendarManager.RANGE_TYPE.LAST_DATE) {
                makeAsSelectedDate(container, type)
            } else if (type == DateRangeCalendarManager.RANGE_TYPE.MIDDLE_DATE) {
                makeAsRangeDate(container)
            } else {
                enabledDayContainer(container)
            }

            container.tvDate.text = date.toString()
        }

        container.tvDate.textSize = 14f
        container.rootView.tag = DayContainer.GetContainerKey(calendar)

    }

    /**
     * To hide date if date is from previous month.
     *
     * @param container - Container
     */
    private fun hideDayContainer(container: DayContainer) {
        container.tvDate.text = ""
        container.tvDate.setBackgroundColor(Color.TRANSPARENT)
        container.strip.setBackgroundColor(Color.TRANSPARENT)
        container.rootView.setBackgroundColor(Color.TRANSPARENT)
        container.rootView.visibility = View.INVISIBLE
        container.rootView.setOnClickListener(null)
    }

    /**
     * To disable past date. Click listener will be removed.
     *
     * @param container - Container
     */
    private fun disableDayContainer(container: DayContainer) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT)
        container.strip.setBackgroundColor(Color.TRANSPARENT)
        container.rootView.setBackgroundColor(Color.TRANSPARENT)
        container.tvDate.setTextColor(calendarStyleAttr!!.disableDateColor)
        container.rootView.visibility = View.VISIBLE
        container.rootView.setOnClickListener(null)
    }

    /**
     * To enable date by enabling click listeners.
     *
     * @param container - Container
     */
    private fun enabledDayContainer(container: DayContainer) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT)
        container.strip.setBackgroundColor(Color.TRANSPARENT)
        container.rootView.setBackgroundColor(Color.TRANSPARENT)
        container.tvDate.setTextColor(calendarStyleAttr!!.defaultDateColor)
        container.rootView.visibility = View.VISIBLE
        container.rootView.setOnClickListener(dayClickListener)
    }

    /**
     * To draw date container as selected as end selection or middle selection.
     *
     * @param container - Container
     * @param stripType - Right end date, Left end date or middle
     */
    private fun makeAsSelectedDate(container: DayContainer, @DateRangeCalendarManager.RANGE_TYPE stripType: Int) {
        val layoutParams = container.strip.layoutParams as RelativeLayout.LayoutParams

        val minDate = dateRangeCalendarManager!!.minSelectedDate
        val maxDate = dateRangeCalendarManager!!.maxSelectedDate

        if (stripType == DateRangeCalendarManager.RANGE_TYPE.START_DATE && maxDate != null &&
                minDate.compareTo(maxDate) != 0) {
            val mDrawable = ContextCompat.getDrawable(context, R.drawable.range_bg_left)
            mDrawable!!.colorFilter = PorterDuffColorFilter(calendarStyleAttr!!.rangeStripColor, FILTER_MODE)

            container.strip.background = mDrawable
            layoutParams.setMargins(20, 0, 0, 0)
        } else if (stripType == DateRangeCalendarManager.RANGE_TYPE.LAST_DATE) {
            val mDrawable = ContextCompat.getDrawable(context, R.drawable.range_bg_right)
            mDrawable!!.colorFilter = PorterDuffColorFilter(calendarStyleAttr!!.rangeStripColor, FILTER_MODE)
            container.strip.background = mDrawable
            layoutParams.setMargins(0, 0, 20, 0)
        } else {
            container.strip.setBackgroundColor(Color.TRANSPARENT)
            layoutParams.setMargins(0, 0, 0, 0)
        }
        container.strip.layoutParams = layoutParams
        val mDrawable = ContextCompat.getDrawable(context, com.globe.havebeen.R.drawable.calendar_circle)
        mDrawable!!.colorFilter = PorterDuffColorFilter(calendarStyleAttr!!.selectedDateCircleColor, FILTER_MODE)
        container.tvDate.background = mDrawable
        container.rootView.setBackgroundColor(Color.TRANSPARENT)
        container.tvDate.setTextColor(calendarStyleAttr!!.selectedDateColor)
        container.rootView.visibility = View.VISIBLE
        container.rootView.setOnClickListener(dayClickListener)
    }

    /**
     * To draw date as middle date
     *
     * @param container - Container
     */
    private fun makeAsRangeDate(container: DayContainer) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT)
        val mDrawable = ContextCompat.getDrawable(context, R.drawable.range_bg)
        mDrawable!!.colorFilter = PorterDuffColorFilter(calendarStyleAttr!!.rangeStripColor, FILTER_MODE)
        container.strip.background = mDrawable
        container.rootView.setBackgroundColor(Color.TRANSPARENT)
        container.tvDate.setTextColor(calendarStyleAttr!!.rangeDateColor)
        container.rootView.visibility = View.VISIBLE
        val layoutParams = container.strip.layoutParams as RelativeLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, 0)
        container.strip.layoutParams = layoutParams
        container.rootView.setOnClickListener(dayClickListener)
    }


    /**
     * To remove all selection and redraw current calendar
     */
    fun resetAllSelectedViews() {

        dateRangeCalendarManager!!.minSelectedDate = null
        dateRangeCalendarManager!!.maxSelectedDate = null

        drawCalendarForMonth(currentCalendarMonth)

    }


    /**
     * To set week title color
     *
     * @param color - resource color value
     */
    fun setWeekTitleColor(@ColorInt color: Int) {
        for (i in 0 until llTitleWeekContainer!!.childCount) {
            val textView = llTitleWeekContainer!!.getChildAt(i) as CustomTextView
            textView.setTextColor(color)
        }
    }

    /**
     * To apply custom fonts to all the text views
     */
    private fun setFonts() {

        drawCalendarForMonth(currentCalendarMonth)

        for (i in 0 until llTitleWeekContainer!!.childCount) {

            val textView = llTitleWeekContainer!!.getChildAt(i) as CustomTextView
            textView.typeface = calendarStyleAttr!!.fonts

        }
    }

}