package barsurfing.nnesterov.ru.barsurfing.data.location

import android.content.Context
import android.location.Location
import android.os.Bundle
import barsurfing.nnesterov.ru.barsurfing.domain.location.LocationProvider
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.functions.Func1
import rx.lang.kotlin.BehaviourSubject
import rx.lang.kotlin.observable
import rx.lang.kotlin.subscriber
import rx.subscriptions.Subscriptions


class LocationProviderImpl(private val context: Context) : LocationProvider {
    private val reactiveProvider = ReactiveLocationProvider(context)


    override fun getLastKnownLocation(): Observable<Location> {
        return reactiveProvider.lastKnownLocation
    }

    override fun getUpdatableLocation(): Observable<Location> {
        val request = LocationRequest()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)

       return reactiveProvider.getUpdatedLocation(request)
    }
}
