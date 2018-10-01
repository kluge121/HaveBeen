package com.globe.havebeen.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R

/**
 * Created by baeminsu on 26/09/2018.
 */


class MainFeedFragment : Fragment() {


    companion object {
        fun newInstance() = MainFeedFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main_feed, container, false)
        with(view) {

        }
        return view
    }
}