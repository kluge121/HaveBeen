package com.globe.havebeen.view.main.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.globe.havebeen.R
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine
import kotlinx.android.synthetic.main.dialog_friend_detail.view.*

/**
 * Created by baeminsu on 30/09/2018.
 */


class FriendDetailDialog : BottomSheetDialogFragment() {


    lateinit var blurEngine: BlurDialogEngine


    companion object {
        fun getInstance(): FriendDetailDialog {
            return FriendDetailDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        blurEngine = BlurDialogEngine(activity)
        with(blurEngine) {
            setBlurRadius(8f.toInt())
            setDownScaleFactor(3f)
        }

        val view = inflater.inflate(R.layout.dialog_friend_detail, container, false)
        with(view) {

            view.friendDetailCloseBtn.setOnClickListener {
                dismiss()
            }

        }
        return view
    }


    override fun onResume() {
        super.onResume()
        blurEngine.onResume(retainInstance)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        blurEngine.onDismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        blurEngine.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (dialog != null) {
            dialog.setDismissMessage(null)
        }
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        dialog!!.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

}
