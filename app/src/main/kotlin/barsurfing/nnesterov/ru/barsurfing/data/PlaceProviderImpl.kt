package barsurfing.nnesterov.ru.barsurfing.data

import barsurfing.nnesterov.ru.barsurfing.domain.Place
import barsurfing.nnesterov.ru.barsurfing.domain.PlaceFilter
import barsurfing.nnesterov.ru.barsurfing.domain.PlaceProvider
import rx.Observable
import java.util.*


class PlaceProviderImpl : PlaceProvider {
    override fun getNearByPlaces(filter: PlaceFilter): Observable<List<Place>> {
        return Observable.just(Collections.singletonList(Place("id", "mock name", 50.0, 50.0)));
    };
}