package ru.nnesterov.barsurfing.data.places

import com.google.maps.android.PolyUtil
import ru.nnesterov.barsurfing.domain.places.Route
import rx.functions.Func1
import java.util.*

internal object RouteMapper : Func1<RouteDto, Route> {
    override fun call(dto: RouteDto?): Route? {
        return Route(PolyUtil.decode(dto?.polyline?.points ?: "") ?: Collections.emptyList())
    }
}