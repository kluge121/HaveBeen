package com.globe.havebeen.view.main.presenter

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainFriendPresenter(friendListView: MainContract.IFriendListView) : MainContract.IFriendPresenter {

    init {
        friendListView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}