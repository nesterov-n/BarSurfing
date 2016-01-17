package ru.nnesterov.barsurfing.data.places

import ru.nnesterov.barsurfing.domain.places.Place
import rx.functions.Func1
import java.util.*


internal object PlaceMapper : Func1<PlacesResponseDto, List<Place>> {

    public override fun call(response: PlacesResponseDto): List<Place> {
        val result = ArrayList<Place>()
        response.results.forEach {
            result.add(Place(it.placeId ?: "",
                    it.name ?: "",
                    it.vicinity ?: "",
                    it.geometry?.location?.lat ?: 0.0,
                    it.geometry?.location?.lng ?: 0.0
            ))
        }
        return result
    }
}