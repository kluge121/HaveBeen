package com.globe.havebeen.view.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.view.ViewGroup
import com.globe.havebeen.R

/**
 * Created by baeminsu on 27/09/2018.
 */


class HomeRoomRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<HomeRoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRoomViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_plan_room, parent, false)
        return HomeRoomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: HomeRoomViewHolder, position: Int) {

    }

}


class HomeRoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {}


class HomeRoomItemDecoration : RecyclerView.ItemDecoration {

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
            outRect!!.set(this.itemOffset!!, 0, this.itemOffset!!, 0)
            //last
        } else if (itemCount > 0 && position == itemCount - 1) {
            outRect!!.set(0, 0, this.itemOffset!!, 0)

        } else {
            outRect!!.set(0, 0, this.itemOffset!!, 0)
        }
    }
}
