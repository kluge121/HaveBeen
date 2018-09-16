package com.globe.havebeen.view.room.create.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.data.model.realm.City
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.view.room.create.RoomCreateActivity
import com.globe.havebeen.view.room.create.RoomCreatePlaceFragment

/**
 * Created by baeminsu on 11/09/2018.
 */
class RoomPlaceListRecyclerViewAdapter(val context: Context, val fragment: RoomCreatePlaceFragment) : RecyclerView.Adapter<PlaceCityViewHolder>() {

    private var arrayList = ArrayList<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceCityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_select_place, parent, false)
        return PlaceCityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: PlaceCityViewHolder, position: Int) {
        holder.setView(arrayList[position])
        holder.deleteBtn.setOnClickListener {
            deleteItem(position)
        }
    }

    fun getItemIdArrayList(): ArrayList<Int> {

        val list = ArrayList<Int>()

        for (i in arrayList) {
            list.add(i.id!!)
        }
        return list
    }

    fun getArrayList(): ArrayList<City> {
        return arrayList
    }

    fun setArrayList(arrayList: ArrayList<City>) {
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    fun addItem(city: City) {
        arrayList.add(city)
        notifyDataSetChanged()
        snackBarCheck()
    }

    private fun deleteItem(position: Int) {
        arrayList.removeAt(position)
        notifyDataSetChanged()
        snackBarCheck()
    }

    fun snackBarCheck() {
        if (arrayList.size == 0) {
            fragment.snackBarDisplay(false, 0)
            (context as RoomCreateActivity).skipBtnHide(false)
            fragment.snackbarFlag = false
        } else if (arrayList.size == 1) {
            fragment.snackBarDisplay(true, 1)
            (context as RoomCreateActivity).skipBtnHide(true)
            fragment.snackbarFlag = true
        } else {
            fragment.snackbarFlag = true
            fragment.snackBarCountChange(arrayList.size)
        }
    }
}

class PlaceCityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val cityName: TextView = view.findViewById(R.id.selectCityNameTv)
    val deleteBtn: ImageButton = view.findViewById(R.id.selectCityDeleteButton)

    fun setView(city: City) {

        val text = "${city.cityKoreanName} ${city.cityEnglishName}"
        cityName.text = text

    }

}