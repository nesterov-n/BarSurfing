package barsurfing.nnesterov.ru.barsurfing.view

import android.content.Context
import android.location.Location
import barsurfing.nnesterov.ru.barsurfing.domain.BarListInteractor
import barsurfing.nnesterov.ru.barsurfing.domain.LocationInteractor
import rx.Subscriber
import rx.Subscription


class MainActivityPresenterImpl(private val  context: Context) : MainActivityPresenter {

    private val barListInteractor: BarListInteractor;
    private val locationInteractor: LocationInteractor;
    private var view: MainView? = null;
    private var subscription : Subscription? = null;

    init {
        barListInteractor = BarListInteractor(context)
        locationInteractor = LocationInteractor(context)
    }

    override fun onCreate(view: MainView) {
        this.view = view
    }


    override fun onStop() {
        subscription?.unsubscribe();
    }

    override fun onDestroy() {
    }

    override fun onMapFullReady() {
        subscription = locationInteractor.subscribeToLocation(LocationSubscriber());
    }

    private inner class LocationSubscriber : Subscriber<Location>() {
        override fun onNext(t: Location?) {
            val location = t ?: return;
            view?.showCurrentLocation(t.latitude, t.longitude);
        }

        override fun onError(e: Throwable?) {
            view?.showError(e);
        }

        override fun onCompleted() {
            unsubscribe();
        }
    }
}