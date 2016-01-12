package barsurfing.nnesterov.ru.barsurfing.view


interface MainActivityPresenter {

    fun onCreate(view: MainView);

    fun onResume();

    fun onPause();

    fun onDestroy();

    fun onMapFullReady();

}