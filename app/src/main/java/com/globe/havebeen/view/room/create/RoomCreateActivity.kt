package com.globe.havebeen.view.room.create

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageButton
import com.globe.havebeen.R
import com.globe.havebeen.data.model.RoomCreateInfo
import com.globe.havebeen.view.base.BaseActivity
import com.globe.havebeen.view.base.adapter.NonSwipeableViewPager
import com.globe.havebeen.view.room.create.adapter.StepViewPagerAdapter
import com.globe.havebeen.view.room.create.dialog.RoomCreateDialog
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import com.globe.havebeen.view.room.create.presenter.RoomCreateFriendPresenter
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
        viewPager.offscreenPageLimit = 4;

        roomCreateStepper.showLabels(false)
        roomCreateStepper.setViewPager(viewPager)

        roomCreatePrev.isEnabled = false
        roomCreatePrev.setOnClickListener {
            onBackPressed()
        }
        roomCreateSkip.setOnClickListener {
            onNextPressed()
        }
        roomCreateBack.setOnClickListener {
            onBackPressed()
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

    private fun backBtnHide(boolean: Boolean) {
        if (boolean) {
            roomCreateBack.isEnabled = true
            roomCreateBack.alpha = 1f
            roomCreatePrev.isEnabled = false
            roomCreatePrev.alpha = 0f
        } else {
            roomCreateBack.isEnabled = false
            roomCreateBack.alpha = 0f
            roomCreatePrev.isEnabled = true
            roomCreatePrev.alpha = 1f
        }
    }

    fun onNextPressed() {
        when (viewPager.currentItem) {
            0 -> {
                viewPager.setCurrentItem(1, false)
                if (roomCreateInfo.startDate == null || roomCreateInfo.endDate == null) {
                    roomCreateInfo.startDate = null
                    roomCreateInfo.endDate = null
                }
                backBtnHide(false)
            }
            1 -> {
                viewPager.setCurrentItem(2, false)
            }
            2 -> {
                viewPager.setCurrentItem(3, false)
            }
            3 -> {
                showCreateDialog()
            }
        }

    }

    override fun onBackPressed() {
        when (viewPager.currentItem) {
            0 -> {
                (stepAdapter.arrayList[0] as RoomCreatePlanFragment).snackbar!!.dismiss()
                finish()
            }
            1 -> {
                viewPager.setCurrentItem(0, false)
                (stepAdapter.arrayList[1] as RoomCreatePlaceFragment).snackbar!!.dismiss()
                backBtnHide(true)
            }
            2 -> {
                viewPager.setCurrentItem(1, false)
                (stepAdapter.arrayList[2] as RoomCreateCostFragment).snackbar.dismiss()
            }
            3 -> {
                viewPager.setCurrentItem(2, false)
                (stepAdapter.arrayList[3] as RoomCreateFriendFragment).snackbar.dismiss()
            }
        }
    }

    override fun showCreateDialog() {
        val intent = Intent(this, RoomCreateDialog::class.java)
        intent.putExtra("info", roomCreateInfo)
        startActivity(intent)
    }


}
