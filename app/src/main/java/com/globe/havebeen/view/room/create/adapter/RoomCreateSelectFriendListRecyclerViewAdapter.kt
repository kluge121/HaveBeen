package com.globe.havebeen.view.room.create.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.data.model.User
import de.hdodenhof.circleimageview.CircleImageView
import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.util.Log
import com.globe.havebeen.view.room.create.RoomCreateActivity
import com.globe.havebeen.view.room.create.RoomCreateFriendFragment

/**
 * Created by baeminsu on 16/09/2018.
 */

class RoomCreateSelectFriendListRecyclerViewAdapter(var context: Context, var fragment: RoomCreateFriendFragment) : RecyclerView.Adapter<SelectFriendViewHolder>() {

    var arrayList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectFriendViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_select_friend_content, parent, false)

        return SelectFriendViewHolder(view)
    }


    override fun onBindViewHolder(holder: SelectFriendViewHolder, position: Int) {
        holder.setView(arrayList[position])
        holder.itemView.setOnClickListener {
            fragment.deleteSelectItem(arrayList[position])
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun addItem(user: User) {
        Log.e("호출", "ㅇㅇㅇ")
        arrayList.add(user)
        (context as RoomCreateActivity).roomCreateInfo.friendList!!.add(user)
        user.isSelected = true
        notifyDataSetChanged()
    }

    fun deleteItem(user: User) {
        arrayList.remove(user)
        (context as RoomCreateActivity).roomCreateInfo.friendList!!.remove(user)
        user.isSelected = false
        notifyDataSetChanged()
    }

}

class SelectFriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var profile: CircleImageView = view.findViewById(R.id.selectFriendContentProfileImage)
    var name: TextView = view.findViewById(R.id.selectFriendContentName)
    fun setView(user: User) {
        name.text = user.name
    }


}

class SelectFriendItemDecoration : RecyclerView.ItemDecoration {

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

        if (position == 0) {
            outRect!!.set(0, 0, 0, 0)
        } else if (itemCount > 0 && position == itemCount - 1) {
            outRect!!.set(this.itemOffset!!, 0, 0, 0)
        } else {
            outRect!!.set(this.itemOffset!!, 0, 0, 0)
        }
    }
}

