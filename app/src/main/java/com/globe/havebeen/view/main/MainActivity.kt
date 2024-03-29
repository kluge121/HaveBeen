package com.globe.havebeen.view.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.globe.havebeen.R
import com.globe.havebeen.extension.disableShiftMode
import com.globe.havebeen.view.main.adapter.MainBottomTabViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        val adapter = MainBottomTabViewPagerAdapter(supportFragmentManager)
        mainActivityViewPager.adapter = adapter
        mainActivityViewPager.currentItem = 0
        mainActivityViewPager.offscreenPageLimit = 4





        initBottomNavigation()


    }

    private fun initBottomNavigation() {
        mainBottomNavigation.disableShiftMode()
        mainBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_tab1 -> {
                    mainActivityViewPager.currentItem = 0
                    mainTabNameTv.text = getString(R.string.tab_journey)
                    mainActivityProfileBtn.visibility = View.VISIBLE
                    mainActivitySettingBtn.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_tab2 -> {
                    mainTabNameTv.text = getString(R.string.tab_friend)
                    mainActivityViewPager.currentItem = 1
                    mainActivityProfileBtn.visibility = View.INVISIBLE
                    mainActivitySettingBtn.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_tab3 -> {
                    mainTabNameTv.text = getString(R.string.tab_alarm)
                    mainActivityViewPager.currentItem = 2
                    mainActivityProfileBtn.visibility = View.INVISIBLE
                    mainActivitySettingBtn.visibility = View.INVISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_tab4 -> {
                    mainTabNameTv.text = getString(R.string.tab_feed)
                    mainActivityViewPager.currentItem = 3
                    mainActivityProfileBtn.visibility = View.INVISIBLE
                    mainActivitySettingBtn.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        val menuView = mainBottomNavigation.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until menuView.childCount) {
            val iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon) as View
            val layoutParams = iconView.getLayoutParams()
            val displayMetrics = resources.displayMetrics
            layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, displayMetrics).toInt()
            layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, displayMetrics).toInt()
            iconView.layoutParams = layoutParams


        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }


}


