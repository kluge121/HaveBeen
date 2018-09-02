package com.globe.havebeen.view.main.presenter

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainFriendListPresenter(friendListView: MainContract.IFriendListView) : MainContract.IFriendListPresenter {

    init {
        friendListView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}