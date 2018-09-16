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
class MainDoingFragment : Fragment(), MainContract.IDoingListView {
    override lateinit var presenter: MainContract.IDoingPresenter

    companion object {
        fun newInstance() = MainDoingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_doing, container, false)
        return view
    }
}