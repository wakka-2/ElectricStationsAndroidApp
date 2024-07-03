package com.app.electricstations.ui.activites

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.electricstations.R
import com.app.electricstations.util.BaseActivity


class MainActivity : BaseActivity() {

    var toolbar:Toolbar ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navController = findNavController(com.app.electricstations.R.id.mainActivityHostFragment)
        NavigationUI.setupWithNavController(toolbar!!, navController, null)

        val window: Window = this.getWindow()


// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)


// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


// finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }

    fun invisibleMainToolBar() {
        toolbar?.visibility = View.INVISIBLE
    }

    fun showMainToolBar() {
        toolbar?.visibility = View.VISIBLE
    }

    fun hideMainToolBar() {
        toolbar?.visibility = View.GONE
    }
}