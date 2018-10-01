package com.globe.havebeen.view.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.globe.havebeen.view.main.MainAlarmFragment
import com.globe.havebeen.view.main.MainFeedFragment
import com.globe.havebeen.view.main.MainFriendFragment
import com.globe.havebeen.view.main.MainHomeFragment

/**
 * Created by baeminsu on 27/09/2018.
 */


class MainBottomTabViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var list = ArrayList<Fragment>()

    init {
        list.add(MainHomeFragment.newInstance())
        list.add(MainFriendFragment.newInstance())
        list.add(MainAlarmFragment.newInstance())
        list.add(MainFeedFragment.newInstance())
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> "홈"
            1 -> "친구"
            2 -> "새소식"
            3 -> "나의 피드"
            else -> super.getPageTitle(position)
        }
    }
}