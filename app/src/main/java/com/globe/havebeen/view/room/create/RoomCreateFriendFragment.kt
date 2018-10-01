package com.globe.havebeen.view.room.create

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.globe.havebeen.R
import com.globe.havebeen.data.model.User
import com.globe.havebeen.extension.getAlphabetCharArray
import com.globe.havebeen.view.room.create.adapter.RoomCreateFriendListRecyclerViewAdapter
import com.globe.havebeen.view.room.create.adapter.RoomCreateSelectFriendListRecyclerViewAdapter
import com.globe.havebeen.view.room.create.adapter.SelectFriendItemDecoration
import com.globe.havebeen.view.room.create.custom.CustomSnackbar
import com.globe.havebeen.view.room.create.presenter.RoomCreateContract
import kotlinx.android.synthetic.main.fragment_room_create_friend.*
import kotlinx.android.synthetic.main.fragment_room_create_friend.view.*
import java.util.*


/**
 * Created by baeminsu on 06/09/2018.
 */
class RoomCreateFriendFragment : Fragment(), RoomCreateContract.IRoomCreateFriendView {

    private lateinit var searchAdapter: RoomCreateFriendListRecyclerViewAdapter
    private lateinit var selectAdapter: RoomCreateSelectFriendListRecyclerViewAdapter
    private lateinit var hash: HashMap<Char, ArrayList<User>>
    private lateinit var snackbarLayout: LinearLayout
    lateinit var snackbar: CustomSnackbar
    lateinit var snackbarTv: TextView
    override lateinit var presenter: RoomCreateContract.IRoomCreateFriendPresenter

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && context != null && view != null) {
            if ((context as RoomCreateActivity).roomCreateInfo.friendList.size > 0) {
                snackBarDisplay(true)
            } else {
                notiHideAndSelectListShow(false)
                snackBarDisplay(false)

            }
        }
    }

    companion object {
        fun newInstance() = RoomCreateFriendFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_room_create_friend, container, false)
        with(view) {
            searchAdapter = RoomCreateFriendListRecyclerViewAdapter(context, this@RoomCreateFriendFragment)
            selectAdapter = RoomCreateSelectFriendListRecyclerViewAdapter(context, this@RoomCreateFriendFragment)
            createRoomFriendListRecyclerView.layoutManager = LinearLayoutManager(context)
            createRoomFriendSelectListRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.createRoomFriendListRecyclerView.adapter = searchAdapter
            this.createRoomFriendSelectListRecyclerView.adapter = selectAdapter
            createRoomFriendSelectListRecyclerView.addItemDecoration(SelectFriendItemDecoration(context, R.dimen.select_friend_space))
            snackbarLayout = view.createRoomFriendSncakContainer
            snackbarTv = view.createRoomFriendSncakTv
            snackbar = CustomSnackbar.make(activity!!.findViewById(android.R.id.content), Snackbar.LENGTH_INDEFINITE)
            presenter.friendListInit()

            createRoomFriendEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val text = p0.toString().toLowerCase(Locale.getDefault())
                    searchAdapter.filter(text)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

            })

            snackbarLayout.setOnClickListener {
                (context as RoomCreateActivity).showCreateDialog()
            }

        }
        return view
    }

    override fun friendListAllUpdate(hash: HashMap<Char, ArrayList<User>>) {

        this.hash = hash
        val chs = arrayOf('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')

        for (i in 0..18) {
            if (hash.containsKey(chs[i])) {
                searchAdapter.allList.addAll(hash[chs[i]] as Collection<User>)

            }
        }
        val iterator = getAlphabetCharArray().iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            if (hash.containsKey(key)) {
                searchAdapter.allList.addAll(hash[key] as Collection<User>)
            }
        }
        searchAdapter.listReset()

    }

    override fun addSelectItem(user: User) {
        if (selectAdapter.itemCount == 0) {
            notiHideAndSelectListShow(true)
            snackBarDisplay(true)
        }
        selectAdapter.addItem(user)
        snackBarCountChange(selectAdapter.itemCount)
        createRoomFriendSelectFriendNotiTv.text = "선택한 동행자 ${selectAdapter.itemCount}"
    }

    override fun deleteSelectItem(user: User) {
        selectAdapter.deleteItem(user)
        snackBarCountChange(selectAdapter.itemCount)
        searchAdapter.notifyDataSetChanged()
        createRoomFriendSelectFriendNotiTv.text = "선택한 동행자 ${selectAdapter.itemCount}"
        if (selectAdapter.itemCount == 0) {
            snackBarDisplay(false)
            notiHideAndSelectListShow(false)
        }
    }


    override fun notiHideAndSelectListShow(boolean: Boolean) {
        if (boolean) {
            createRoomFriendTv1.visibility = View.INVISIBLE
            createRoomFriendTv2.visibility = View.INVISIBLE
            createRoomFriendSelectFriendNotiTv.visibility = View.VISIBLE
            createRoomFriendSelectListRecyclerView.visibility = View.VISIBLE

        } else {
            createRoomFriendTv1.visibility = View.VISIBLE
            createRoomFriendTv2.visibility = View.VISIBLE
            createRoomFriendSelectFriendNotiTv.visibility = View.INVISIBLE
            createRoomFriendSelectListRecyclerView.visibility = View.INVISIBLE

        }
    }

    fun snackBarDisplay(boolean: Boolean) {
        if (boolean) {
            showSnackBar(snackbarLayout)
            (activity as RoomCreateActivity).skipBtnHide(true)
        } else {
            hideSnackBar(snackbarLayout)
            (activity as RoomCreateActivity).skipBtnHide(false)
        }
    }

    fun snackBarCountChange(itemCount: Int) {
        snackbarTv.text = "동행자 ${itemCount}명 함께하기 / 초대"
    }

    fun hideSnackBar(view: View) {
        ViewCompat.setScaleY(view, 1f);
        ViewCompat.animate(view)
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationEnd(view: View?) {
                        view!!.visibility = View.GONE
                    }

                    override fun onAnimationCancel(view: View?) {
                    }

                    override fun onAnimationStart(view: View?) {

                    }
                })
                .scaleY(0f)
                .setDuration(300)
                .startDelay = 0

    }

    fun showSnackBar(view: View) {
        ViewCompat.setScaleY(view, 0f);
        ViewCompat.animate(view)
                .setListener((object : ViewPropertyAnimatorListener {
                    override fun onAnimationEnd(view: View?) {

                    }

                    override fun onAnimationCancel(view: View?) {
                    }

                    override fun onAnimationStart(view: View?) {
                        view!!.visibility = View.VISIBLE
                    }

                }))
                .scaleY(1f)
                .setDuration(300)
                .startDelay = 0
    }
}
