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
class MainAfterFragment : Fragment(), MainContract.IAfterView {
    override lateinit var presenter: MainContract.IAfterPresenter


    companion object {
        fun newInstance() = MainAfterFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_after, container, false)
        return view
    }
}