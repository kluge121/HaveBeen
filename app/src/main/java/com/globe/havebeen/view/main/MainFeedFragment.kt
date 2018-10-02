package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.FeedStoryRecyclerViewAdapter
import com.globe.havebeen.view.main.presenter.MainContract
import kotlinx.android.synthetic.main.fragment_main_feed.*
import kotlinx.android.synthetic.main.fragment_main_feed.view.*

/**
 * Created by baeminsu on 26/09/2018.
 */


class MainFeedFragment() : Fragment(), MainContract.IFeedView {

    override lateinit var presenter: MainContract.IFeedPresenter

    lateinit var adapter: FeedStoryRecyclerViewAdapter


    companion object {
        fun newInstance() = MainFeedFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main_feed, container, false)
        with(view) {

            adapter = FeedStoryRecyclerViewAdapter(context)
            tabFeedRecyclerView.adapter = adapter
            tabFeedRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


            view.tabFeedDoingStoryButtonContainer.setOnClickListener {
                buttonTabChange(0)
                //TODO 리사이클러뷰 업데이트
            }

            view.tabFeedLastStoryButtonContainer.setOnClickListener {
                buttonTabChange(1)
                //TODO 리사이클러뷰 업데이트
            }

        }
        return view
    }


    override fun buttonTabChange(position: Int) {

        when (position) {
            0 -> {
                tabFeedDoingStoryTv.setTextColor(ContextCompat.getColor(context!!, R.color.aqua))
                tabFeedDoingStoryIv.visibility = View.VISIBLE
                tabFeedLastStoryTv.setTextColor(ContextCompat.getColor(context!!, R.color.c8))
                tabFeedLastStoryIv.visibility = View.INVISIBLE
            }
            1 -> {
                tabFeedLastStoryTv.setTextColor(ContextCompat.getColor(context!!, R.color.aqua))
                tabFeedLastStoryIv.visibility = View.VISIBLE

                tabFeedDoingStoryTv.setTextColor(ContextCompat.getColor(context!!, R.color.c8))
                tabFeedDoingStoryIv.visibility = View.INVISIBLE
            }

        }

    }

}

