package ru.nnesterov.barsurfing.domain.places

import android.content.Context
import ru.nnesterov.barsurfing.data.location.LocationProviderImpl
import ru.nnesterov.barsurfing.data.places.PlaceProviderImpl
import ru.nnesterov.barsurfing.domain.location.LocationProvider
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class BarListInteractor(private val placeProvider: PlaceProvider,
                        private val locationProvider: LocationProvider) {
    val SEARCH_RADIUS_IN_METERS = 3000;

    // TODO: Add DI
    constructor(context: Context) : this(PlaceProviderImpl(context), LocationProviderImpl(context))

    public fun getNearbyBars(subscriber: Subscriber<List<Place>>): Subscription {
        return locationProvider.getLastKnownLocation().flatMap {
            val filter = PlaceFilter(PlaceType.BAR,
                    it.latitude,
                    it.longitude,
                    SEARCH_RADIUS_IN_METERS)
            placeProvider.getNearByPlaces(filter)
        }.map {
            // max 10 places
            it.subList(0, Math.min(it.size - 1, 10))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }
}