package com.globe.havebeen.view.room.create.custom

import android.support.constraint.ConstraintLayout
import android.support.design.widget.BaseTransientBottomBar
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.globe.havebeen.R


/**
 * Created by baeminsu on 09/09/2018.
 */


class CustomSnackbar(parent: ViewGroup, content: View, contentViewCallback: BaseTransientBottomBar.ContentViewCallback) : BaseTransientBottomBar<CustomSnackbar>(parent, content, contentViewCallback) {


    fun setAction(text: CharSequence, listener: View.OnClickListener): CustomSnackbar {
        val actionView = view.findViewById<View>(R.id.roomCreateSnackbarContainer) as ConstraintLayout
        actionView.visibility = View.VISIBLE
        actionView.setOnClickListener({ view ->
            listener.onClick(view)
            // Now dismiss the Snackbar
            dismiss()
        })
        return this
    }

    fun setText(text: CharSequence): CustomSnackbar {
        val textView: TextView = view.findViewById(R.id.roomCreateSnackbarTv)
        textView.text = text
        return this
    }


    companion object {
        fun make(parent: ViewGroup, duration: Int): CustomSnackbar {
            val inflater = LayoutInflater.from(parent.context)
            val content = inflater.inflate(R.layout.layout_room_create_snackbar, parent, false)

            val callback = ContentViewCallback(content)
            val customSnackbar = CustomSnackbar(parent, content, callback)
            customSnackbar.view.setPadding(0, 0, 0, 0)
            customSnackbar.duration = duration
            return customSnackbar
        }

        private class ContentViewCallback(var content: View) : BaseTransientBottomBar.ContentViewCallback {

            override fun animateContentOut(delay: Int, duration: Int) {
                ViewCompat.setScaleY(content, 1f);
                ViewCompat.animate(content)
                        .scaleY(0f)
                        .setDuration(duration.toLong())
                        .startDelay = delay.toLong()
            }

            override fun animateContentIn(delay: Int, duration: Int) {
                ViewCompat.setScaleY(content, 0f);
                ViewCompat.animate(content)
                        .scaleY(1f).setDuration(duration.toLong())
                        .startDelay = delay.toLong();
            }
        }

    }


}