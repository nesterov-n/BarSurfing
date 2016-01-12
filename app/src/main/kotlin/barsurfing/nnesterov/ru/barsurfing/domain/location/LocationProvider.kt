package barsurfing.nnesterov.ru.barsurfing.domain.location

import android.location.Location
import rx.Observable


interface LocationProvider {
    fun getLastKnownLocation(): Observable<Location>
}