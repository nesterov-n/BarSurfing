package ru.nnesterov.barsurfing.data.places.utils

import com.google.android.gms.maps.model.LatLng


fun LatLng.toRequestParam() = "$latitude,$longitude"