package com.globe.havebeen.view.main.presenter

/**
 * Created by baeminsu on 02/09/2018.
 */
class MainAlarmListPresenter(alarmListView: MainContract.IAlarmListView) : MainContract.IAlarmListPresenter {
    init {
        alarmListView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}