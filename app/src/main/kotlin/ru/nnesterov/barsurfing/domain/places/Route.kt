package ru.nnesterov.barsurfing.domain.places

import com.google.android.gms.maps.model.LatLng


data class Route(val points: List<LatLng>)
