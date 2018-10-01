package com.globe.havebeen.view.main

import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.FriendRecyclerViewAdapter
import com.globe.havebeen.view.main.presenter.MainContract
import kotlinx.android.synthetic.main.fragment_main_friend.view.*

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainFriendFragment : Fragment(), MainContract.IFriendListView {
    override lateinit var presenter: MainContract.IFriendPresenter

    lateinit var adapter: FriendRecyclerViewAdapter

    companion object {
        fun newInstance() = MainFriendFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_friend, container, false)

        with(view) {
            adapter = FriendRecyclerViewAdapter(context)

            view.tabFriendRecyclerView.adapter = adapter
            view.tabFriendRecyclerView.layoutManager = LinearLayoutManager(context)

        }
        return view
    }
}