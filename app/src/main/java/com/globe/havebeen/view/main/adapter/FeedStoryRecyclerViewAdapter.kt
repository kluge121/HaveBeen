package com.globe.havebeen.view.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.globe.havebeen.R
import kotlinx.android.synthetic.main.recyclerview_item_feed_story.view.*


class FeedStoryRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<FeedStoryViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return (position % 2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedStoryViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_feed_story, parent, false)

        if (viewType == 1) {

            return DoingStoryViewHolder(view)
        } else {
            return LastStoryViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: FeedStoryViewHolder, position: Int) {
        holder.setView()

    }


}


abstract class FeedStoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun setView()
}


class DoingStoryViewHolder(view: View) : FeedStoryViewHolder(view) {

    var imageView: ImageView = view.feedStoryIv
    override fun setView() {
        imageView.setImageResource(R.drawable.logo)

    }
}

class LastStoryViewHolder(view: View) : FeedStoryViewHolder(view) {

    var imageView: ImageView = view.feedStoryIv
    override fun setView() {
        imageView.setImageResource(R.drawable.a2)

    }

}


