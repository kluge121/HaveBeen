package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.globe.havebeen.R
import com.globe.havebeen.view.room.create.custom.CustomSnackbar
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import kotlinx.android.synthetic.main.fragment_room_create_cost.*
import java.text.DecimalFormat
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**
 * Created by baeminsu on 06/09/2018.
 */
class RoomCreateCostFragment : Fragment(), RoomCreateContract.IRoomCreateCostView {


    var tmpText: String = ""
    lateinit var createRoomCostEditTextCost: EditText
    lateinit var snackbar: CustomSnackbar
    var period: Int? = null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && context != null) {
            if ((context as RoomCreateActivity).tripDayPeriod != null) {
                period = (context as RoomCreateActivity).tripDayPeriod
            }

            if (!createRoomCostEditTextCost.text.toString().isBlank()) {
                (context as RoomCreateActivity).skipBtnHide(true)
                snackbar.setText("예산 확정")
                snackbar.show()
            } else {
                (context as RoomCreateActivity).skipBtnHide(false)
                snackbar.dismiss()
            }
        } else {
            if (context != null) {
                val inputMethodManager = (context as RoomCreateActivity).getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(createRoomCostEditTextCost.getWindowToken(), 0)
            }


        }
    }

    companion object {
        fun newInstance() = RoomCreateCostFragment()
    }

    override lateinit var presenter: RoomCreateContract.IRoomCreateFriendPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_room_create_cost, container, false)

        with(view) {
            snackbar = CustomSnackbar.make(activity!!.findViewById(android.R.id.content), Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("금엑 추가", View.OnClickListener {
                (context as RoomCreateActivity).roomCreateInfo.cost = tmpText.replace(",", "").toInt()
                (context as RoomCreateActivity).onNextPressed()
            })
            createRoomCostEditTextCost = findViewById(R.id.createRoomCostEditTextCost)
            val decimalFormat = DecimalFormat("#,###")

            createRoomCostEditTextCost.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                    if (p0.toString().isEmpty()) {
                        snackbar.dismiss()
                        dayCostShow(false)
                    } else {
                        (context as RoomCreateActivity).skipBtnHide(true)
                    }

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!p0.isNullOrBlank() && !(p0.toString() == tmpText)) {
                        if (!snackbar.isShown) {
                            snackbar.show()
                            dayCostShow(true)
                        }
                        tmpText = decimalFormat.format(java.lang.Double.parseDouble(p0.toString().replace(",", "")))
                        createRoomCostEditTextCost.setText(tmpText)
                        createRoomCostEditTextCost.setSelection(tmpText.length)
                        snackbar.setText("예산 확정")
                        makeCostOfDayText(p0.toString().replace(",", "").toInt())
                    } else {
                        (context as RoomCreateActivity).skipBtnHide(false)
                    }

                }
            })

        }
        return view
    }


    fun dayCostShow(boolean: Boolean) {

        if (boolean) {
            createRoomCostContainer3.alpha = 1f
        } else {
            createRoomCostContainer3.alpha = 0f
        }

    }

    override fun makeCostOfDayText(price: Int) {
        if (period != null) {
            createRoomCostDayNotiTv.text = "${period}박${period!! + 1}일 여행 시 \n일일 평균 지출금액은"
            val intCost = (price.toString().replace(",", "").toInt() / (period!! + 1))
            val longCost = intCost.toLong()
            val decimalFormat = DecimalFormat("#,###")
            createRoomCostDayCostTv.text = decimalFormat.format(longCost)


        }
    }


}