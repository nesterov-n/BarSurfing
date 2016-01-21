/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

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
