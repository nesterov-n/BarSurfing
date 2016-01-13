package barsurfing.nnesterov.ru.barsurfing.view

import android.os.Bundle


interface MainActivityPresenter {

    fun onCreate(view: MainView, savedState: Bundle?);

    fun onPause();

    fun onStop();

    fun onDestroy();

    fun onMapFullReady();

}