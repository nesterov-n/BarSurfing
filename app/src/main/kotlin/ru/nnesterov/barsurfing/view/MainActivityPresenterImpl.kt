package ru.nnesterov.barsurfing.view

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import ru.nnesterov.barsurfing.R
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
        if (routedPLaces == null) {
            startLoading()
        } else {
            view?.showLoading()
            view?.showPlaces(routedPLaces!!)
        }
    }

    private fun startLoading() {
        view?.showLoading()
        subscriptions.add(locationInteractor.subscribeToLocation(LocationSubscriber()));
        subscriptions.add(barListInteractor.getNearbyBars(PlacesSubscriber()));
    }

    override fun onAboutOverlayClicked() {
        view?.hideAbout()
    }

    override fun onErrorOverlayClicked() {
        view?.hideError()
        startLoading()
    }

    override fun onSaveInstanceState(state: Bundle) {
        if (routedPLaces != null) {
            val serializablaeWrapper = RoutedPlacesWrapper(routedPLaces!!)
            state.putSerializable(ROUTE_KEY, serializablaeWrapper)
        }
    }

    private abstract inner class BaseSubscriber<T> : Subscriber<T>() {
        override abstract fun onNext(t: T)

        override fun onError(e: Throwable?) {
            Log.e("MainActivityPresenterImpl", "", e)
            val errorText = context.getString(R.string.network_error)
            view?.showError(errorText)
        }

        override fun onCompleted() {
            unsubscribe()
        }
    }

    private inner class LocationSubscriber : BaseSubscriber<Location>() {
        override fun onNext(location: Location) {
            view?.showCurrentLocation(location.latitude, location.longitude);
        }
    }

    private inner class PlacesSubscriber : BaseSubscriber<RoutedPlaces>() {
        override fun onNext(places: RoutedPlaces) {
            routedPLaces = places
            view?.showPlaces(places)
        }
    }
}