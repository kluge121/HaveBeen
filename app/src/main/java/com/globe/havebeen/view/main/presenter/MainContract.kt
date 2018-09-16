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
    interface IBeforePresenter : BasePresenter

    interface IBeforeView : BaseView<IBeforePresenter>

    //친구 탭 프래그먼트
    interface IDoingPresenter : BasePresenter

    interface IDoingListView : BaseView<IDoingPresenter>

    //알람 탭 프래그먼트
    interface IAfterPresenter : BasePresenter

    interface IAfterView : BaseView<IAfterPresenter>


}