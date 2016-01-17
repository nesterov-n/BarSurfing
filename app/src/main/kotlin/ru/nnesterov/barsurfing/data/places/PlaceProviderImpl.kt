package ru.nnesterov.barsurfing.data.places

import android.content.Context
import retrofit.RestAdapter
import ru.nnesterov.barsurfing.R
import ru.nnesterov.barsurfing.domain.places.Place
import ru.nnesterov.barsurfing.domain.places.PlaceFilter
import ru.nnesterov.barsurfing.domain.places.PlaceProvider
import ru.nnesterov.barsurfing.domain.places.PlaceType
import rx.Observable


public class PlaceProviderImpl(private val context: Context) : PlaceProvider {
    private val placesApi = RestAdapter.Builder()
            .setEndpoint("https://maps.googleapis.com/maps/api")
            .setLogLevel(RestAdapter.LogLevel.NONE)
            .build()
            .create(PlacesApi::class.java);

    private val key = context.resources.getString(R.string.google_places_key)

    override fun getNearByPlaces(filter: PlaceFilter): Observable<List<Place>> {
        val stringLocation = "" + filter.lattitude + "," + filter.longitude;
        val stringType = when (filter.placeType) {
            PlaceType.BAR -> "bar"
            PlaceType.RESTAURANT -> "restaurant"
        }
        val rankby = "distance"

        return placesApi.getPlaces(stringLocation,
                stringType,
                rankby,
                true,
                key)
                .map(PlaceMapper)
    };
}