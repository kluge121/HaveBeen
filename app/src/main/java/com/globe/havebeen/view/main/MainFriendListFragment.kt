package com.globe.havebeen.view.main

import android.support.v4.app.Fragment
import com.globe.havebeen.view.main.presenter.MainContract

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainFriendListFragment : Fragment(), MainContract.IFriendListView {
    override lateinit var presenter: MainContract.IFriendListPresenter

}