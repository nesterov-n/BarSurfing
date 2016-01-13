package barsurfing.nnesterov.ru.barsurfing.view

import android.content.Context
import android.location.Location
import android.os.Bundle
import barsurfing.nnesterov.ru.barsurfing.domain.location.LocationInteractor
import barsurfing.nnesterov.ru.barsurfing.domain.places.BarListInteractor
import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import rx.Subscriber
import rx.Subscription
import java.util.*


class MainActivityPresenterImpl(private val  context: Context) : MainActivityPresenter {

    private val barListInteractor: BarListInteractor;
    private val locationInteractor: LocationInteractor;
    private var view: MainView? = null;
    private val subscriptions = ArrayList<Subscription>()

    init {
        barListInteractor = BarListInteractor(context)
        locationInteractor = LocationInteractor(context)
    }

    override fun onCreate(view: MainView, savedState: Bundle?) {
        this.view = view
    }

    override fun onStop() {
        subscriptions.forEach {
            it.unsubscribe()
        }
        subscriptions.clear();
    }

    override fun onPause() {

    }

    override fun onDestroy() {
    }

    override fun onMapFullReady() {
        view?.showLoading()
        subscriptions.add(locationInteractor.subscribeToLocation(LocationSubscriber()));
        subscriptions.add(barListInteractor.getNearbyBars(PlacesSubscriber()));
    }

    private inner class LocationSubscriber : Subscriber<Location>() {
        override fun onNext(t: Location?) {
            val location = t ?: return;
            view?.showCurrentLocation(location.latitude, location.longitude);
        }

        override fun onError(e: Throwable?) {
            view?.showError(e);
        }

        override fun onCompleted() {
            unsubscribe();
        }
    }

    private inner class PlacesSubscriber : Subscriber<List<Place>>() {
        override fun onNext(places: List<Place>) {
            view?.showPlaces(places)
        }

        override fun onError(e: Throwable) {
            view?.showError(e);
        }

        override fun onCompleted() {
            unsubscribe()
        }
    }
}