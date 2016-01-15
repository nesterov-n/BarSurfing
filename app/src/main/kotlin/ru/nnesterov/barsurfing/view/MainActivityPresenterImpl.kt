package ru.nnesterov.barsurfing.view

import android.content.Context
import android.location.Location
import android.os.Bundle
import ru.nnesterov.barsurfing.domain.location.LocationInteractor
import ru.nnesterov.barsurfing.domain.places.BarListInteractor
import ru.nnesterov.barsurfing.domain.places.RoutedPlaces
import rx.Subscriber
import rx.Subscription
import java.util.*


class MainActivityPresenterImpl(private val  context: Context) : MainActivityPresenter {

    private val ROUTE_KEY = "route-key"

    private val barListInteractor: BarListInteractor;
    private val locationInteractor: LocationInteractor;
    private var view: MainView? = null;
    private val subscriptions = ArrayList<Subscription>()
    private var routedPLaces: RoutedPlaces? = null

    init {
        barListInteractor = BarListInteractor(context)
        locationInteractor = LocationInteractor(context)
    }

    override fun onCreate(view: MainView, savedState: Bundle?) {
        this.view = view
        val wrapper = savedState?.getSerializable(ROUTE_KEY) as RoutedPlacesWrapper?
        if (wrapper != null) {
            routedPLaces = wrapper.toRoutedPlaces()
        }
        if (savedState == null) {
            view.showAboutOverlay()
        }
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
        if (routedPLaces == null) {
            subscriptions.add(locationInteractor.subscribeToLocation(LocationSubscriber()));
            subscriptions.add(barListInteractor.getNearbyBars(PlacesSubscriber()));
        } else {
            view?.showPlaces(routedPLaces!!)
        }
    }

    override fun onAboutOverlayClicked() {
        view?.hideAboutOverlay()
    }

    override fun onSaveinstanceState(state: Bundle) {
        if (routedPLaces != null) {
            val serializablaeWrapper = RoutedPlacesWrapper(routedPLaces!!)
            state.putSerializable(ROUTE_KEY, serializablaeWrapper)
        }
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

    private inner class PlacesSubscriber : Subscriber<RoutedPlaces>() {
        override fun onNext(places: RoutedPlaces) {
            routedPLaces = places
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