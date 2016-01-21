/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.domain.places

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import ru.nnesterov.barsurfing.data.location.LocationProviderImpl
import ru.nnesterov.barsurfing.data.places.PlaceProviderImpl
import ru.nnesterov.barsurfing.data.places.RouteProviderImpl
import ru.nnesterov.barsurfing.domain.location.LocationProvider
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class BarListInteractor(private val placeProvider: PlaceProvider,
                        private val locationProvider: LocationProvider,
                        private val routeProvider: RouteProvider) {

    // TODO: Add DI
    constructor(context: Context) : this(PlaceProviderImpl(context), LocationProviderImpl(context), RouteProviderImpl(context))

    public fun getNearbyBars(subscriber: Subscriber<RoutedPlaces>): Subscription {
        val location: Observable<Location> = locationProvider.getLastKnownLocation()

        val routeFilter: Observable<RouteFilterHolder> = location.flatMap {
            val locationFilter = PlaceFilter(PlaceType.BAR,
                    it.latitude,
                    it.longitude)
            placeProvider.getNearByPlaces(locationFilter)
                    // max 10 places
                    .map { placeList -> placeList.subList(0, Math.min(10, placeList.size - 1)) }
                    .flatMap { Observable.just(RouteFilterHolder(locationFilter.lattitude, locationFilter.longitude, it)) }
        }

        val routedPlaces = routeFilter.flatMap { holder ->
            routeProvider.createRoute(holder.routeFilter)
                    .flatMap {
                        Observable.just(RoutedPlaces(holder.places, it))
                    }
        }

        return routedPlaces
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    private data class RouteFilterHolder(val currentLattitude: Double,
                                         val currentLongitude: Double,
                                         val places: List<Place>) {
        val routeFilter: RouteFilter

        init {
            // To visit all places by fastest way we start route from our location
            // through all points and finish it at current location too
            val currentLatLng = LatLng(currentLattitude, currentLongitude)
            routeFilter = RouteFilter(currentLatLng,
                    currentLatLng,
                    places.map { LatLng(it.latitude, it.longitude) })
        }
    }

}

