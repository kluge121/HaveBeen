package com.globe.havebeen.view.room.create.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.data.model.User
import com.globe.havebeen.view.room.create.RoomCreateFriendFragment
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * Created by baeminsu on 16/09/2018.
 */


class RoomCreateFriendListRecyclerViewAdapter(var context: Context, var fragment: RoomCreateFriendFragment) : RecyclerView.Adapter<ContentViewHolder>() {

    var searchList = ArrayList<User>()
    var allList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_search_freind_content, parent, false)

        return ContentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.setView(searchList[position])
        holder.checkBox.setOnClickListener {
            if (searchList[position].isSelected) {
                fragment.deleteSelectItem(searchList[position])
            } else {
                fragment.addSelectItem(searchList[position])
            }
        }
    }

    fun filter(text: String) {

        val charText = text.toLowerCase(Locale.getDefault())
        searchList.clear()
        if (text.isBlank()) {
            listReset()
            return
        }
        for (i in allList) {
            if (i.name!!.toLowerCase().contains(charText)) {
                searchList.add(i)
            }
        }
        notifyDataSetChanged()
    }

    fun listReset() {
        searchList.clear()
        searchList.addAll(allList as Collection<User>)
        notifyDataSetChanged()
    }

}

class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val profile: CircleImageView = view.findViewById(R.id.searchFriendContentProfileImage)
    val name: TextView = view.findViewById(R.id.searchFriendContentName)
    val checkBox: CheckBox = view.findViewById(R.id.searchFriendContentCheckBox)

    fun setView(user: User) {
        name.text = user.name
        checkBox.isChecked = user.isSelected
    }
}