package com.globe.havebeen.view.main.presenter



class MainFeedPresenter(feedView: MainContract.IFeedView) : MainContract.IFeedPresenter{

    init {
        feedView.presenter = this
    }
    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}