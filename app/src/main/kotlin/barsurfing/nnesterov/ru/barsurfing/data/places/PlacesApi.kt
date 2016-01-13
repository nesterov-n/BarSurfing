package barsurfing.nnesterov.ru.barsurfing.data.places

import retrofit.http.GET
import retrofit.http.Query
import rx.Observable


internal interface PlacesApi {
    @GET("/nearbysearch/json")
    fun getPlaces(@Query("location") location: String,
                  @Query("types") types: String,
                  @Query("rankby") rankby: String,
                  @Query("opennow") openNow: Boolean,
                  @Query("key") key: String): Observable<PlacesResponse>
}