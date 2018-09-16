package com.globe.havebeen.view.room.create

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.globe.havebeen.R
import com.globe.havebeen.data.model.realm.City
import com.globe.havebeen.view.room.create.adapter.RoomPlaceListRecyclerViewAdapter
import com.globe.havebeen.view.room.create.custom.CustomSnackbar
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import io.realm.Realm

/**
 * Created by baeminsu on 06/09/2018.
 */


class RoomCreatePlaceFragment : Fragment(), RoomCreateContract.IRoomCreatePlaceView {

    lateinit var adapater: RoomPlaceListRecyclerViewAdapter
    var snackbar: CustomSnackbar? = null
    var snackbarFlag: Boolean = false


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && context != null) {

            if ((context as RoomCreateActivity).roomCreateInfo.cityList != null && (context as RoomCreateActivity).roomCreateInfo.cityList!!.size > 0) {
                snackbar!!.setText("총 여행지 ${(context as RoomCreateActivity).roomCreateInfo.cityList!!.size}곳 / 확정")
                (context as RoomCreateActivity).skipBtnHide(true)
                adapater.setArrayList((context as RoomCreateActivity).roomCreateInfo.cityList!!)
                snackbar!!.show()
            } else {
                (context as RoomCreateActivity).skipBtnHide(false)
                snackbar!!.dismiss()
            }

        }
    }

    companion object {
        fun newInstance() = RoomCreatePlaceFragment()
    }

    override lateinit var presenter: RoomCreateContract.IRoomCreatePlacePresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_room_create_place, container, false)
        val fragment = this
        with(view) {
            val createRoomPlaceRecyclerView: RecyclerView = view.findViewById(R.id.createRoomPlaceRecyclerView)
            adapater = RoomPlaceListRecyclerViewAdapter(context, fragment)
            createRoomPlaceRecyclerView.adapter = adapater
            createRoomPlaceRecyclerView.layoutManager = LinearLayoutManager(context)
            val createRoomPlaceCityEditText: EditText = findViewById(R.id.createRoomPlaceCityEditText)
            createRoomPlaceCityEditText.setOnClickListener {
                val intent = Intent(context, RoomCitySearch::class.java)
                intent.putIntegerArrayListExtra("selectList", adapater.getItemIdArrayList())
                startActivityForResult(intent, 101)
            }

            snackbar = CustomSnackbar.make(activity!!.findViewById(android.R.id.content), Snackbar.LENGTH_INDEFINITE)
            snackbar!!.setAction("장소 추가", View.OnClickListener {
                (context as RoomCreateActivity).roomCreateInfo.cityList = adapater.getArrayList()
                (context as RoomCreateActivity).onNextPressed()
            })

        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 101 && resultCode == RESULT_OK) {

            val addCityId = data!!.getIntExtra("selectItem", -1)

            val realm = Realm.getDefaultInstance()
            val realmQuery = realm.where(City::class.java)
            val result = realmQuery.equalTo("id", addCityId).findAll()
            adapater.addItem(result.get(0)!!)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun snackBarDisplay(boolean: Boolean, itemCount: Int) {
        if (boolean) {
            snackbar!!.setText("총 여행지 ${itemCount}곳 / 확정")
            snackbar!!.show()
        } else {
            snackbar!!.dismiss()
        }
    }

    fun snackBarCountChange(itemCount: Int) {
        snackbar!!.setText("총 여행지 ${itemCount}곳 / 확정")
    }


}
