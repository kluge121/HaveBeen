package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.HomeRoomItemDecoration
import com.globe.havebeen.view.main.adapter.HomeRoomRecyclerViewAdapter
import com.globe.havebeen.view.main.presenter.MainContract
import kotlinx.android.synthetic.main.fragment_main_home.view.*

/**
 * Created by baeminsu on 02/09/2018.
 */


class MainHomeFragment : Fragment(), MainContract.IHomeView {
    override lateinit var presenter: MainContract.IHomePresenter

    companion object {
        fun newInstance() = MainHomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)

        with(view) {
            view.tabHomeRecyclerView.adapter = HomeRoomRecyclerViewAdapter(context)
            view.tabHomeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            view.tabHomeRecyclerView.addItemDecoration(HomeRoomItemDecoration(context, R.dimen.home_room_space))
        }

        return view
    }
}
