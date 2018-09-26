package com.globe.havebeen.view.room.create.adapter


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.support.v7.widget.RecyclerView
import android.view.*
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.util.Log
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.data.model.realm.City
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

/**
 * Created by baeminsu on 10/09/2018.
 */


class CitySearchRecyclerViewAdapter(var context: Context, var selectList: ArrayList<Int>) : RecyclerView.Adapter<CitySearchViewHolder>() {


    private lateinit var allList: RealmResults<City>
    private var searchList: List<City> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_search_city, parent, false)

        return CitySearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) {
        holder.setView(searchList[position])


        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selectItem", searchList[position].id)
            (context as Activity).setResult(RESULT_OK, intent)
            (context as Activity).finish()

        }

    }


    fun defaultClear() {
        (searchList as ArrayList).clear()
        val query = Realm.getDefaultInstance().where(City::class.java)
        query.equalTo("cityKoreanName", "인기 도시")
        val result = query.findAll()
        (searchList as ArrayList<City>).addAll(Realm.getDefaultInstance().copyFromRealm(result))
        Log.e("체크", "${searchList.size} 개")
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        allList = Realm.getDefaultInstance().where(City::class.java).findAll()

        val charText = text.toLowerCase(Locale.getDefault())
        (searchList as ArrayList).clear()
        if (text.isEmpty()) {
            (searchList as ArrayList).clear()
            return
        }
        loop@ for (i in allList) {
            val cityKoreanName = i.cityKoreanName
            val cityEnglishName = i.cityEnglishName
            val cityCountry = i.cityCountry

            for (j in selectList) {
                //TODO 동명의 도시이면 어떻게 처리할지?
                if (j == i.id && j == i.id)
                    continue@loop
            }
            if (cityKoreanName!!.toLowerCase().contains(charText)) {
                (searchList as ArrayList).add(i)
                continue
            } else if (cityEnglishName!!.toLowerCase().contains(charText)) {
                (searchList as ArrayList).add(i)
                continue
            } else if (cityCountry!!.toLowerCase().contains(charText)) {
                (searchList as ArrayList).add(i)
                continue
            }
        }
        notifyDataSetChanged()
    }
}


class CitySearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var cityTv: TextView = view.findViewById(R.id.searchCityName)
    private var countryTv: TextView = view.findViewById(R.id.searchCountryName)

    fun setView(city: City) {
        val text = "${city.cityKoreanName} ${city.cityEnglishName}"
        cityTv.text = text
        countryTv.text = city.cityCountry
    }

}

class SearchPlaceItemDecoration : RecyclerView.ItemDecoration {

    var itemOffset: Int? = null

    constructor(itemOffset: Int) {
        this.itemOffset = itemOffset
    }

    constructor(context: Context, @DimenRes itemOffsetId: Int) {
        this.itemOffset = context.resources.getDimensionPixelOffset(itemOffsetId)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        val position = parent!!.getChildAdapterPosition(view)
        val itemCount = state!!.itemCount


        // first
        if (position == 0) {
            outRect!!.set(0, 0, 0, this.itemOffset!!)
            //last
        } else if (itemCount > 0 && position == itemCount - 1) {
            outRect!!.set(0, 0, 0, 0)

        } else {
            outRect!!.set(0, 0, 0, this.itemOffset!!)
        }
    }
}



