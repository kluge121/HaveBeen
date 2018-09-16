package com.globe.havebeen.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.MainFragmentViewPagerAdpater
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter = MainFragmentViewPagerAdpater(supportFragmentManager, this)
        mainViewpager.adapter = viewPagerAdapter

        BottomNavigationViewHelper.disableShiftMode(mainBottomNavigationView)
        mainBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_one -> return@setOnNavigationItemSelectedListener true
                R.id.action_two -> return@setOnNavigationItemSelectedListener true
                R.id.action_three -> return@setOnNavigationItemSelectedListener true
                else -> {
                    return@setOnNavigationItemSelectedListener false

                }
            }
        }

    }


}


class BottomNavigationViewHelper {
    companion object {

        @SuppressLint("RestrictedApi")
        fun disableShiftMode(view: BottomNavigationView) {

            val menuView = view.getChildAt(0) as BottomNavigationMenuView

            try {
                val shiftingMode: Field = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i: Int in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    item.setPadding(0, 20, 0, 0)
                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: NoSuchFileException) {
            } catch (e: IllegalAccessException) {
            }
        }

    }
}

