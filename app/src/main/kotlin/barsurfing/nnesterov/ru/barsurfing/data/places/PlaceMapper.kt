package barsurfing.nnesterov.ru.barsurfing.data.places

import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import rx.functions.Func1
import java.util.*


internal object PlaceMapper : Func1<PlacesResponse, List<Place>> {

    public override fun call(response: PlacesResponse): List<Place> {
        val result = ArrayList<Place>()
        response.results.forEach {
            result.add(Place(it.placeId ?: "",
                    it.name ?: "",
                    it.geometry?.location?.lat ?: 0.0,
                    it.geometry?.location?.lng ?: 0.0
            ))
        }
        return result
    }
}