package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.havebeen.R
import com.globe.havebeen.data.model.User
import com.globe.havebeen.util.CenterLayoutManager
import com.globe.havebeen.view.room.create.adapter.RoomCreateFriendListRecyclerView
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import kotlinx.android.synthetic.main.fragment_room_create_friend.*
import kotlinx.android.synthetic.main.fragment_room_create_friend.view.*

/**
 * Created by baeminsu on 06/09/2018.
 */
class RoomCreateFriendFragment : Fragment(), RoomCreateContract.IRoomCreateFriendView {

    private lateinit var adapter: RoomCreateFriendListRecyclerView
    private lateinit var hash: HashMap<Char, ArrayList<User>>


    companion object {
        fun newInstance() = RoomCreateFriendFragment()
    }

    override lateinit var presenter: RoomCreateContract.IRoomCreateFriendPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_room_create_friend, container, false)

        with(view) {
            adapter = RoomCreateFriendListRecyclerView(context)
            createRoomFriendListRecyclerView.layoutManager = CenterLayoutManager(context)
            this.createRoomFriendListRecyclerView.adapter = adapter
            presenter.friendListInit()

        }
        return view
    }

    override fun friendListAllUpdate(hash: HashMap<Char, ArrayList<User>>) {

        this.hash = hash
        val chs = arrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')

        for (i in 0..18) {
            if (hash.containsKey(chs[i])) {
                adapter.addSection(adapter.ExpandableFriendSection(chs[i], hash[chs[i]]!!))
            }
        }
        adapter.notifyDataSetChanged()
    }


    override fun notiHideAndSelectListShow(boolean: Boolean) {
        if (boolean) {
            createRoomFriendSelectListRecyclerView.visibility = View.VISIBLE
            createRoomFriendTv1.visibility = View.INVISIBLE
            createRoomFriendTv2.visibility = View.INVISIBLE
        } else {
            createRoomFriendSelectListRecyclerView.visibility = View.INVISIBLE
            createRoomFriendTv1.visibility = View.VISIBLE
            createRoomFriendTv2.visibility = View.VISIBLE
        }
    }


}
