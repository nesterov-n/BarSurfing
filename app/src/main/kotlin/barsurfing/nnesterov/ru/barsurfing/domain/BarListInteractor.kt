package barsurfing.nnesterov.ru.barsurfing.domain

import android.content.Context
import barsurfing.nnesterov.ru.barsurfing.data.location.LocationProviderImpl
import barsurfing.nnesterov.ru.barsurfing.data.places.PlaceProviderImpl
import barsurfing.nnesterov.ru.barsurfing.domain.location.LocationProvider
import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceFilter
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceProvider
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceType
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class BarListInteractor(private val placeProvider: PlaceProvider,
                        private val locationProvider: LocationProvider) {
    val SEARCH_RADIUS_IN_METERS = 3000;

    // TODO: Add DI
    constructor(context: Context) : this(PlaceProviderImpl(), LocationProviderImpl(context))


    public fun getNearbyBars(subscriber: Subscriber<List<Place>>): Subscription {
        return locationProvider.getLastKnownLocation().flatMap {

            val filter = PlaceFilter(PlaceType.BAR,
                    it.latitude,
                    it.longitude,
                    SEARCH_RADIUS_IN_METERS)
            placeProvider.getNearByPlaces(filter)

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }
}