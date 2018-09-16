package com.globe.havebeen.view.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.graphics.Rect
import com.globe.havebeen.R

/**
 * Created by baeminsu on 02/09/2018.
 */


class MainRoomListAdapter(val context: Context) : RecyclerView.Adapter<MainRoomListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRoomListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_room, parent, false)
        return MainRoomListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: MainRoomListViewHolder, position: Int) {

    }


}


class MainRoomListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}

open class GirdSpaceDecoration : RecyclerView.ItemDecoration() {
    private val space: Int = 10

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect!!.left = space
        outRect.bottom = space
        outRect.top = space
        outRect.right = space


    }
}