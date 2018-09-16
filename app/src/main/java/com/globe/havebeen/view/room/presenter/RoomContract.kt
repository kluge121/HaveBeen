package com.globe.havebeen.view.room.presenter

import com.globe.havebeen.BasePresenter
import com.globe.havebeen.BaseView

/**
 * Created by baeminsu on 04/09/2018.
 */
interface RoomContract {

    interface IRoomPresenter : BasePresenter
    interface IRoomView : BaseView<IRoomPresenter>

    interface IRoomActionSequencePresenter : BasePresenter

    interface IRoomLodgmentSequencePresenter : BasePresenter
    interface IRoomLodgmentSequenceView : BaseView<IRoomLodgmentSequencePresenter>

    interface RoomTripSequencePresenter : BasePresenter
    interface RoomTripSequenceView : BaseView<RoomTripSequencePresenter>


}