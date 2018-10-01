package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.view.main.presenter.MainContract

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainAlarmFragment : Fragment(), MainContract.IAlarmView {
    override lateinit var presenter: MainContract.IAlarmPresenter


    companion object {
        fun newInstance() = MainAlarmFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_alarm, container, false)
        return view
    }
}