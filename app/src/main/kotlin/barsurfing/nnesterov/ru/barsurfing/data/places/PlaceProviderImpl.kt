package barsurfing.nnesterov.ru.barsurfing.data.places

import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceFilter
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceProvider
import rx.Observable
import java.util.*


class PlaceProviderImpl : PlaceProvider {
    override fun getNearByPlaces(filter: PlaceFilter): Observable<List<Place>> {
        return Observable.just(Collections.singletonList(Place("id", "mock name", 50.0, 50.0)))
    };
}