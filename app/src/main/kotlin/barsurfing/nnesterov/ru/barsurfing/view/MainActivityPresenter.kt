package barsurfing.nnesterov.ru.barsurfing.view

import android.os.Bundle


interface MainActivityPresenter {

    fun onCreate(view: MainView, savedState: Bundle?);

    fun onStop();

    fun onDestroy();

    fun onMapFullReady();

}