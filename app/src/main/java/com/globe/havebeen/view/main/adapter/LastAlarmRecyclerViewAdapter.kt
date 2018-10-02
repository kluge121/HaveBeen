package com.globe.havebeen.view.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.MainActivity
import com.globe.havebeen.view.main.dialog.FriendDetailDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.recyclerview_item_arrow_alarm.view.*
import kotlinx.android.synthetic.main.recyclerview_item_btn_alarm.view.*


class LastAlarmRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<AlarmViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            0
        } else {
            1
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {

        val view: View

        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_arrow_alarm, parent, false)
            return ArrowAlarmHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_btn_alarm, parent, false)
            return BtnAlarmHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {

        if (holder is ArrowAlarmHolder) {
            holder.profile.setOnClickListener {
                FriendDetailDialog.getInstance().show((context as MainActivity).supportFragmentManager, "")
            }
        } else if (holder is BtnAlarmHolder) {
            holder.profile.setOnClickListener {
                FriendDetailDialog.getInstance().show((context as MainActivity).supportFragmentManager, "")
            }
        }
    }

}

abstract class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun setView()
}


class BtnAlarmHolder(view: View) : AlarmViewHolder(view) {
    val profile: CircleImageView = view.buttonAlarmProfileIv
    override fun setView() {

    }

}

class ArrowAlarmHolder(view: View) : AlarmViewHolder(view) {

    val profile: CircleImageView = view.arrowAlarmProfileIv

    override fun setView() {

    }

}

