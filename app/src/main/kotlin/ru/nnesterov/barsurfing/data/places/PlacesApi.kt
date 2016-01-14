package ru.nnesterov.barsurfing.data.places

import retrofit.http.GET
import retrofit.http.Query
import rx.Observable


internal interface PlacesApi {
    @GET("/place/nearbysearch/json")
    fun getPlaces(@Query("location") location: String,
                  @Query("types") types: String,
                  @Query("rankby") rankby: String,
                  @Query("opennow") openNow: Boolean,
                  @Query("key") key: String): Observable<PlacesResponseDto>

    @GET("/directions/json")
    fun getRoute(@Query("origin") origin: String,
                 @Query("destination") destination: String,
                 @Query("waypoints") via: String,
                 @Query("mode") mode: String,
                 @Query("key") key: String): Observable<RouteResponseDto>
}