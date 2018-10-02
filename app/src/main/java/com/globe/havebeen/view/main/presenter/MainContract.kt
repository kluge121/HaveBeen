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
    interface IHomePresenter : BasePresenter

    interface IHomeView : BaseView<IHomePresenter>

    //친구 탭 프래그먼트
    interface IFriendPresenter : BasePresenter

    interface IFriendListView : BaseView<IFriendPresenter>

    //알람 탭 프래그먼트
    interface IAlarmPresenter : BasePresenter

    interface IAlarmView : BaseView<IAlarmPresenter>


    interface IFeedPresenter : BasePresenter
    interface IFeedView : BaseView<IFeedPresenter> {
        fun buttonTabChange(position: Int)
    }


}