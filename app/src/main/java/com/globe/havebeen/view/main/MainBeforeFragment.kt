package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.GirdSpaceDecoration
import com.globe.havebeen.view.main.adapter.MainRoomListAdapter
import com.globe.havebeen.view.main.presenter.MainContract
import kotlinx.android.synthetic.main.fragment_main_before.view.*

/**
 * Created by baeminsu on 02/09/2018.
 */


class MainBeforeFragment : Fragment(), MainContract.IBeforeView {
    override lateinit var presenter: MainContract.IBeforePresenter

    companion object {
        fun newInstance() = MainBeforeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_before, container, false)
        with(view) {
            mainMapListRecyclerView.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
            mainMapListRecyclerView.adapter = MainRoomListAdapter(context)
            mainMapListRecyclerView.addItemDecoration(GirdSpaceDecoration())
        }
        return view
    }
}
