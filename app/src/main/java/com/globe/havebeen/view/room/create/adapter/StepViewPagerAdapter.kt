package com.globe.havebeen.view.room.create.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.globe.havebeen.view.room.create.RoomCreateCostFragment
import com.globe.havebeen.view.room.create.RoomCreateFriendFragment
import com.globe.havebeen.view.room.create.RoomCreatePlaceFragment
import com.globe.havebeen.view.room.create.RoomCreatePlanFragment

/**
 * Created by baeminsu on 06/09/2018.
 */
class StepViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    var arrayList = ArrayList<Fragment>()

    init {
        arrayList.add(RoomCreatePlanFragment.newInstance())
        arrayList.add(RoomCreatePlaceFragment.newInstance())
        arrayList.add(RoomCreateCostFragment.newInstance())
        arrayList.add(RoomCreateFriendFragment.newInstance())
    }

    override fun getItem(position: Int): Fragment? {
        return arrayList.get(position)
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "여행일정"
            1 -> return "여행지"
            2 -> return "여행예산"
            3 -> return "동행자"

        }
        return super.getPageTitle(position)
    }
}