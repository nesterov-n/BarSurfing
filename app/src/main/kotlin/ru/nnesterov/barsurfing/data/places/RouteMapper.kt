package ru.nnesterov.barsurfing.data.places

import com.google.maps.android.PolyUtil
import ru.nnesterov.barsurfing.domain.places.Route
import rx.functions.Func1
import java.util.*

internal object RouteMapper : Func1<RouteDto, Route> {
    override fun call(dto: RouteDto?): Route? {
        val encodedRoute = dto?.polyline?.points ?: ""
        val points = PolyUtil.decode(encodedRoute) ?: Collections.emptyList()
        val distance = dto?.legs?.fold(0) {
            totalDistance, leg ->
            totalDistance + (leg.distance?.value ?: 0)
        } ?: 0
        return Route(points, distance)
    }
}