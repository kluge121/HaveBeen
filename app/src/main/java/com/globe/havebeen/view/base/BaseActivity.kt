package com.globe.havebeen.view.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.globe.havebeen.R
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by baeminsu on 25/08/2018.
 */
open class BaseActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar

    fun setToolbar(resId: Int, isSetIndicator: Boolean) {

        toolbar = findViewById(resId)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        if (isSetIndicator) {
            setHomeAsUpIndicator()
        }

    }

    private fun setHomeAsUpIndicator() {

        if (toolbar == null) return

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }

    }

    fun setToolbarTitle(toolbarTitle: String) {
        text_toolbar_title.text = toolbarTitle
    }

}