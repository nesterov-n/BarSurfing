package barsurfing.nnesterov.ru.barsurfing.view

import android.content.Context
import barsurfing.nnesterov.ru.barsurfing.domain.BarListInteractor


class MainActivityPresenterImpl(private val  context: Context) : MainActivityPresenter {

    private val interactor: BarListInteractor;

    init {
        interactor = BarListInteractor(context)
    }

    override fun onCreate(view: MainView) {
        throw UnsupportedOperationException()
    }

    override fun onResume() {
        throw UnsupportedOperationException()
    }

    override fun onPause() {
        throw UnsupportedOperationException()
    }

    override fun onDestroy() {
        throw UnsupportedOperationException()
    }

    override fun onMapFullReady() {
        throw UnsupportedOperationException()
    }
}