package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.adapter.LastAlarmRecyclerViewAdapter
import com.globe.havebeen.view.main.adapter.NewAlarmRecyclerViewAdapter
import com.globe.havebeen.view.main.presenter.MainContract
import kotlinx.android.synthetic.main.fragment_main_alarm.view.*

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainAlarmFragment : Fragment(), MainContract.IAlarmView {
    override lateinit var presenter: MainContract.IAlarmPresenter


    lateinit var newAdapter: NewAlarmRecyclerViewAdapter
    lateinit var lastAdapter: LastAlarmRecyclerViewAdapter


    companion object {
        fun newInstance() = MainAlarmFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_alarm, container, false)

        with(view) {

            newAdapter = NewAlarmRecyclerViewAdapter(context)
            lastAdapter = LastAlarmRecyclerViewAdapter(context)

            with(view.tabAlarmNewsRecyclerView) {
                adapter = newAdapter
                layoutManager = LinearLayoutManager(context)

            }
            with(view.tabAlarmLastRecyclerView) {
                adapter = lastAdapter
                layoutManager = LinearLayoutManager(context)
            }


        }
        return view
    }
}