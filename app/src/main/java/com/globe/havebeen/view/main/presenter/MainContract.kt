package com.globe.havebeen.view.main.presenter

import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView

/**
 * Created by baeminsu on 02/09/2018.
 */
interface MainContract {


    //메인 액티비티
    interface IMainPresenter : BasePresenter

    interface IMainView : BaseView<IMainPresenter>

    //룸 탭 프래그먼트
    interface IRoomListPresenter : BasePresenter

    interface IRoomListView : BaseView<IRoomListPresenter>

    //친구 탭 프래그먼트
    interface IFriendListPresenter : BasePresenter

    interface IFriendListView : BaseView<IFriendListPresenter>

    //알람 탭 프래그먼트
    interface IAlarmListPresenter : BasePresenter

    interface IAlarmListView : BaseView<IAlarmListPresenter>


}