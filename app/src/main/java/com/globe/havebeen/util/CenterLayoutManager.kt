package com.globe.havebeen.util

import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager

import android.content.Context
import android.util.AttributeSet


/**
 * Created by baeminsu on 16/09/2018.
 */
class CenterLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val smoothScroller = CenterSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private class CenterSmoothScroller internal constructor(context: Context) : LinearSmoothScroller(context) {

        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
            return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
        }
    }
}