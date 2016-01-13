package barsurfing.nnesterov.ru.barsurfing.data.location

import android.content.Context
import android.location.Location
import barsurfing.nnesterov.ru.barsurfing.domain.location.LocationProvider
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.lang.kotlin.observable
import rx.lang.kotlin.subscriber
import rx.subscriptions.Subscriptions


class LocationProviderImpl(private val context: Context) : LocationProvider {
    val googleApiClient: GoogleApiClient

    init {
        googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build()
    }

    override fun getLastKnownLocation(): Observable<Location> {
        return observable { subscriber ->
            val location = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

            if (location != null) {
                subscriber.onNext(location);
                subscriber.onCompleted();
            } else {
                subscriber.onError(RuntimeException("Cannot determine location"));
            }
        }
    }

    override fun getUpdatableLocation(): Observable<Location> {
        return observable { subscriber ->
            val locationListener = LocationListener { location ->
                subscriber.onNext(location);
            }
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(googleApiClient,
                            LocationRequest(),
                            locationListener)
            subscriber.add(Subscriptions.create {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
            })
        }
    }
}
