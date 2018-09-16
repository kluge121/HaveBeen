package com.globe.havebeen.view.main.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.globe.havebeen.view.main.MainAfterFragment
import com.globe.havebeen.view.main.MainDoingFragment
import com.globe.havebeen.view.main.MainBeforeFragment

/**
 * Created by baeminsu on 02/09/2018.
 */


class MainFragmentViewPagerAdpater(val fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> MainBeforeFragment.newInstance()
            1 -> MainDoingFragment.newInstance()
            2 -> MainAfterFragment.newInstance()
            else -> {
                null
            }
        }

    }

    override fun getCount(): Int {
        return 3
    }


}