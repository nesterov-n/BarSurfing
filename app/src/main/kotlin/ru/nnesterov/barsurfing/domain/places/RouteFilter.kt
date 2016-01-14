package ru.nnesterov.barsurfing.domain.places

import com.google.android.gms.maps.model.LatLng


data class RouteFilter(val origin: LatLng,
                       val destination: LatLng,
                       val via: List<LatLng>)