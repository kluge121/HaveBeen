package com.globe.havebeen.extend

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.globe.havebeen.base.BaseActivity

/**
 * Created by baeminsu on 25/08/2018.
 */
fun BaseActivity.replaceFragment(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun BaseActivity.addFragment(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        add(frameId, fragment)
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}
fun AppCompatActivity.removeFragmentById(@IdRes frameId: Int) {
    supportFragmentManager.transact {
        remove(supportFragmentManager.findFragmentById(frameId))
    }
}