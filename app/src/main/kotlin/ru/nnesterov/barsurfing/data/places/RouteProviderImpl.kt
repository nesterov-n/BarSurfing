package ru.nnesterov.barsurfing.data.places

import android.content.Context
import retrofit.RestAdapter
import ru.nnesterov.barsurfing.R
import ru.nnesterov.barsurfing.data.places.utils.toRequestParam
import ru.nnesterov.barsurfing.domain.places.Route
import ru.nnesterov.barsurfing.domain.places.RouteFilter
import ru.nnesterov.barsurfing.domain.places.RouteProvider
import rx.Observable

class RouteProviderImpl(context: Context) : RouteProvider {
    private val key = context.getString(R.string.google_places_key)

    // TODO: DI
    private val placesApi = RestAdapter.Builder()
            .setEndpoint("https://maps.googleapis.com/maps/api")
            .setLogLevel(RestAdapter.LogLevel.NONE)
            .build()
            .create(PlacesApi::class.java);

    override fun createRoute(filter: RouteFilter): Observable<Route> {
        val origin = filter.origin.toRequestParam()
        val destination = filter.destination.toRequestParam()
        var waypoints = "optimize:true"
        filter.via.forEach { waypoints = waypoints + "|" + it.toRequestParam() }

        val getFirstRouteOrEmpty: (RouteResponseDto) -> Observable<RouteDto> = {
            if (it.results.size > 0) {
                Observable.just(it.results[0])
            } else {
                Observable.empty()
            }
        }

        return placesApi.getRoute(origin, destination, waypoints, "walking", key)
                .flatMap(getFirstRouteOrEmpty)
                .switchIfEmpty(placesApi.getRoute(origin, destination, waypoints, "driving", key)
                        .flatMap(getFirstRouteOrEmpty))
                .map(RouteMapper)
    }
}