package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.data.model.RoomCreateInfo
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.room.create.adapter.StepViewPagerAdapter
import com.globe.havebeen.view.room.create.custom.NonSwipeableViewPager
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import com.globe.havebeen.view.room.create.presenter.RoomCreateFriendPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_room_create.*

/**
 * Created by baeminsu on 05/09/2018.
 */

class RoomCreateActivity : BaseActivity(), RoomCreateContract.IRoomCreateView {

    private lateinit var fragment: Fragment
    private lateinit var viewPager: NonSwipeableViewPager
    private lateinit var stepAdapter: StepViewPagerAdapter
    override lateinit var presenter: RoomCreateContract.IRoomCreatePresenter
    private lateinit var roomCreateSkip: ImageButton
    public var roomCreateInfo = RoomCreateInfo()
    var tripDayPeriod: Int? = null


    lateinit var roomCreateFriendPresenter: RoomCreateContract.IRoomCreateFriendPresenter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_create)
        roomCreateSkip = findViewById<ImageButton>(R.id.roomCreateSkip)
        viewPager = findViewById(R.id.roomCreateContainer)
        stepAdapter = StepViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = stepAdapter
        roomCreateStepper.showLabels(false)
        roomCreateStepper.setViewPager(viewPager)

        roomCreatePrev.setOnClickListener {
            onBackPressed()
        }
        roomCreateSkip.setOnClickListener {
            onNextPressed()
        }


        roomCreateFriendPresenter = RoomCreateFriendPresenter(stepAdapter.arrayList[3] as RoomCreateFriendFragment)
    }

    fun skipBtnHide(boolean: Boolean) {
        if (boolean) {
            roomCreateSkip.isEnabled = false
            roomCreateSkip.alpha = 0f
        } else {
            roomCreateSkip.isEnabled = true
            roomCreateSkip.alpha = 1f
        }
    }

    fun onNextPressed() {
        when (viewPager.currentItem) {
            0 -> {
                viewPager.setCurrentItem(1, false)
            }
            1 -> {
                viewPager.setCurrentItem(2, false)
            }
            2 -> {
                viewPager.setCurrentItem(3, false)
            }
        }

    }

    override fun onBackPressed() {
        when (viewPager.currentItem) {
            0 -> {
                finish()
            }
            1 -> {
                viewPager.setCurrentItem(0, false)
            }
            2 -> {
                viewPager.setCurrentItem(1, false)
            }
            3 -> {
                viewPager.setCurrentItem(2, false)
            }
        }
    }


}
