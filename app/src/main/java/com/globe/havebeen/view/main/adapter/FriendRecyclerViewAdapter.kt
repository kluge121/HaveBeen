package com.globe.havebeen.view.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.globe.havebeen.R
import com.globe.havebeen.extension.takeScreenShot
import com.globe.havebeen.view.base.BlurView
import com.globe.havebeen.view.main.MainActivity
import com.globe.havebeen.view.main.dialog.FriendDetailDialog

/**
 * Created by baeminsu on 29/09/2018.
 */


class FriendRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<FriendViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_friend, parent, false)
        return FriendViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        holder.itemView.setOnClickListener {

            FriendDetailDialog.getInstance().show((context as MainActivity).supportFragmentManager, "")

        }

    }
}

class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {


}

