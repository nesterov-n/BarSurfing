package ru.nnesterov.barsurfing.view

import com.google.android.gms.maps.model.LatLng
import ru.nnesterov.barsurfing.domain.places.Place
import ru.nnesterov.barsurfing.domain.places.Route
import ru.nnesterov.barsurfing.domain.places.RoutedPlaces
import java.io.Serializable
import java.util.*

/**
 *  Hacke class to encapsulate serialization problems
 */
internal class RoutedPlacesWrapper(routedPlaces: RoutedPlaces) : Serializable {

    private val serializationUUID: Long = 248L

    private val internalPlaces = ArrayList<InternalPlace>();

    private val internalRoutePoints = ArrayList<InternalPoint>()

    init {
        routedPlaces.route.points.forEach {
            internalRoutePoints.add(InternalPoint(it.latitude, it.longitude))
        }

        routedPlaces.places.forEach {
            internalPlaces.add(InternalPlace(it.id,
                    it.name,
                    InternalPoint(it.latitude, it.longitude)))
        }
    }

    internal fun toRoutedPlaces(): RoutedPlaces {
        val places = ArrayList<Place>(internalPlaces.size)
        internalPlaces.forEach {
            places.add(Place(it.id,
                    it.name,
                    it.location.latitude,
                    it.location.longitude
            ))
        }

        val routePoints = ArrayList<LatLng>(internalRoutePoints.size)
        internalRoutePoints.forEach {
            routePoints.add(LatLng(it.latitude,
                    it.longitude))
        }

        return RoutedPlaces(places, Route(routePoints))
    }

    private data class InternalPoint(val latitude: Double,
                                     val longitude: Double) : Serializable {
        private val serializationUUID: Long = 249L
    }

    private data class InternalPlace(val id: String,
                                     val name: String,
                                     val location: InternalPoint) : Serializable {
        private val serializationUUID: Long = 250L
    }


}



