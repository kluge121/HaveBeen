package com.globe.havebeen.view.room.create.presenter

import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView
import com.globe.havebeen.data.model.User
import com.globe.havebeen.data.model.realm.City
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by baeminsu on 05/09/2018.
 */

interface RoomCreateContract {

    interface IRoomCreatePresenter : BasePresenter
    interface IRoomCreateView : BaseView<IRoomCreatePresenter> {

        fun showCreateDialog()
    }

    interface IRoomCreatePlanPresenter : BasePresenter
    interface IRoomCreatePlanView : BaseView<IRoomCreatePlanPresenter> {
        fun planMakeText(startDate: Calendar, endDate: Calendar): String
    }

    interface IRoomCreatePlacePresenter : BasePresenter
    interface IRoomCreatePlaceView : BaseView<IRoomCreatePlacePresenter> {
    }

    interface IRoomCreateCostPresenter : BasePresenter
    interface IRoomCreateCostView : BaseView<IRoomCreateFriendPresenter> {
        fun makeCostOfDayText(price: Int)
    }


    interface IRoomCreateFriendPresenter : BasePresenter {
        fun friendListInit()
        fun initialSort(list: ArrayList<User>): HashMap<Char, ArrayList<User>>
    }

    interface IRoomCreateFriendView : BaseView<IRoomCreateFriendPresenter> {
        fun notiHideAndSelectListShow(boolean: Boolean)
        fun addSelectItem(user: User)
        fun deleteSelectItem(user: User)
        fun friendListAllUpdate(hash: HashMap<Char, ArrayList<User>>)
    }


    interface IRoomCitySearch : BasePresenter
    interface IRoomCityView : BaseView<IRoomCitySearch>

}