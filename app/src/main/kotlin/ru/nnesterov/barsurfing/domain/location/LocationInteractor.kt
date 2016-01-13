package ru.nnesterov.barsurfing.domain.location

import android.content.Context
import android.location.Location
import ru.nnesterov.barsurfing.data.location.LocationProviderImpl
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LocationInteractor(private val locationProvider: LocationProvider) {
    constructor(context: Context) : this(LocationProviderImpl(context));

    public fun subscribeToLocation(subscriber: Subscriber<Location>): Subscription {
        return locationProvider.getUpdatableLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
