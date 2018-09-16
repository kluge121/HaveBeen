package com.globe.havebeen.view.room.create.presenter

import android.util.Log
import com.globe.havebeen.data.model.User
import com.globe.havebeen.extension.getInitialSound
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by baeminsu on 15/09/2018.
 */


class RoomCreateFriendPresenter(var friendView: RoomCreateContract.IRoomCreateFriendView) : RoomCreateContract.IRoomCreateFriendPresenter {
    init {
        friendView.presenter = this
    }


    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun friendListInit() {
        val db = FirebaseFirestore.getInstance()
        db.collection("user").get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val list = it.result.toObjects(User::class.java) as ArrayList<User>
                        initialSort(list)
                    } else {
                    }
                }
    }

    override fun initialSort(list: ArrayList<User>): HashMap<Char, ArrayList<User>> {
        val hash = HashMap<Char, ArrayList<User>>()

        for (user in list) {
            val initial = user.name!!.getInitialSound()
            if (!hash.containsKey(initial)) {
                if (initial != null) {
                    hash[initial] = ArrayList()
                }
            }
            hash[initial]!!.add(user)
        }

        friendView.friendListAllUpdate(hash)
        return hash

    }


}