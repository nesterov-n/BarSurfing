package barsurfing.nnesterov.ru.barsurfing.data.location

import android.content.Context
import android.location.Location
import barsurfing.nnesterov.ru.barsurfing.domain.location.LocationProvider
import rx.Observable


class LocationProviderImpl(private val context: Context) : LocationProvider {
    override fun getLastKnownLocation(): Observable<Location> {
        return Observable.just(Location("NETWORK"))
    }
}