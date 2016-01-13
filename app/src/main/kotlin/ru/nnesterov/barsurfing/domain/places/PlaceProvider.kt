package ru.nnesterov.barsurfing.domain.places

import rx.Observable


interface PlaceProvider {
    fun getNearByPlaces(filter: PlaceFilter): Observable<List<Place>>
}