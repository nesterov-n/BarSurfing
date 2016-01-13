package barsurfing.nnesterov.ru.barsurfing.data.places

import android.content.Context
import barsurfing.nnesterov.ru.barsurfing.R
import barsurfing.nnesterov.ru.barsurfing.domain.places.Place
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceFilter
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceProvider
import barsurfing.nnesterov.ru.barsurfing.domain.places.PlaceType
import retrofit.RestAdapter
import rx.Observable


public class PlaceProviderImpl(private val context: Context) : PlaceProvider {
    private val placesApi = RestAdapter.Builder()
            .setEndpoint("https://maps.googleapis.com/maps/api")
            .setLogLevel(RestAdapter.LogLevel.FULL)
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