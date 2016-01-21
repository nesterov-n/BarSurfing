/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

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
    private var routedPlaces: RoutedPlaces? = null

    init {
        barListInteractor = BarListInteractor(context)
        locationInteractor = LocationInteractor(context)
    }

    override fun onCreate(view: MainView, savedState: Bundle?) {
        this.view = view
        val wrapper = savedState?.getSerializable(ROUTE_KEY) as RoutedPlacesWrapper?
        if (wrapper != null) {
            routedPlaces = wrapper.toRoutedPlaces()
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
        if (routedPlaces == null) {
            startLoading()
        } else {
            view?.showLoading()
            view?.showPlaces(routedPlaces!!)
        }
    }

    override fun onPlaceVisitedChanged(placeId: String) {
        if (routedPlaces?.places == null) {
            return
        }
        var hasChanges = false;

        routedPlaces?.places?.forEach {
            if (it.id == placeId) {
                it.visited = !it.visited
                hasChanges = true;
            }
        }

        if (hasChanges) {
            view?.showPlaces(routedPlaces!!)
        }
    }

    override fun onAboutOverlayClicked() {
        view?.hideAbout()
    }

    override fun onErrorOverlayClicked() {
        view?.hideError()
        startLoading()
    }

    override fun onSaveInstanceState(state: Bundle) {
        if (routedPlaces != null) {
            val serializablaeWrapper = RoutedPlacesWrapper(routedPlaces!!)
            state.putSerializable(ROUTE_KEY, serializablaeWrapper)
        }
    }

    private fun startLoading() {
        view?.showLoading()
        //        subscriptions.add(locationInteractor.subscribeToLocation(LocationSubscriber()));
        subscriptions.add(barListInteractor.getNearbyBars(PlacesSubscriber()));
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
            routedPlaces = places
            view?.showPlaces(places)
        }
    }
}