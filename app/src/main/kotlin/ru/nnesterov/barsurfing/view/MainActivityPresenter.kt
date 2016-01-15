package ru.nnesterov.barsurfing.view

import android.os.Bundle


interface MainActivityPresenter {

    fun onCreate(view: MainView, savedState: Bundle?)

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onSaveinstanceState(state: Bundle)

    fun onMapFullReady()

    fun onAboutOverlayClicked()
}