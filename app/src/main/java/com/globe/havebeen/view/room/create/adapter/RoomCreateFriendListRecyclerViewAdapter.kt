package com.globe.havebeen.view.room.create.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.globe.havebeen.data.model.User
import android.content.Context
import android.widget.TextView
import com.globe.havebeen.R
import de.hdodenhof.circleimageview.CircleImageView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

/**
 * Created by baeminsu on 16/09/2018.
 */


class RoomCreateFriendListRecyclerView(var context: Context) : SectionedRecyclerViewAdapter() {


    inner class ExpandableFriendSection(var headerPlace: Char, private val list: ArrayList<User>) : StatelessSection(SectionParameters.builder()
            .itemResourceId(R.layout.recyclerview_item_search_freind_content)
            .headerResourceId(R.layout.recyclerview_item_search_freind_header)
            .build()) {

        private var expanded = true

        override fun getContentItemsTotal(): Int {
            return if (expanded)
                list.size
            else
                0
        }

        override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
            return HeaderViewHolder(view!!)
        }

        override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
            return ContentViewHolder(view!!)
        }


        override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
            val headerHolder = holder as HeaderViewHolder
            headerHolder.setView(headerPlace)
        }

        override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            val contentHolder = holder as ContentViewHolder
            contentHolder.setView(list[position])

        }

    }


}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tv: TextView = view.findViewById(R.id.searchFriendHeaderTv)
    fun setView(char: Char) {
        tv.text = char.toString()
    }
}

class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val profile: CircleImageView = view.findViewById(R.id.searchFriendContentProfileImage)
    val name: TextView = view.findViewById(R.id.searchFriendContentName)
    val message: TextView = view.findViewById(R.id.searchFriendContentAddInfo)

    fun setView(user: User) {
        name.text = user.name
    }
}