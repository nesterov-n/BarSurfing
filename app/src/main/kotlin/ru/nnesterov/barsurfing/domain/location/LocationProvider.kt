package ru.nnesterov.barsurfing.domain.location

import android.location.Location
import rx.Observable


interface LocationProvider {
    fun getLastKnownLocation(): Observable<Location>

    fun getUpdatableLocation(): Observable<Location>
}