package barsurfing.nnesterov.ru.barsurfing.domain

import rx.Observable


interface PlaceProvider {
    fun getNearByPlaces(filter: PlaceFilter): Observable<List<Place>>;
}