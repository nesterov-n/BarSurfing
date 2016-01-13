package barsurfing.nnesterov.ru.barsurfing.view


interface MainActivityPresenter {

    fun onCreate(view: MainView);

    fun onStop();

    fun onDestroy();

    fun onMapFullReady();

}