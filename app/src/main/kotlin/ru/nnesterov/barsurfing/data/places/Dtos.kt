/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

internal class GeometryDto {

    @SerializedName("location")
    @Expose
    var location: LocationDto? = null

}


class DistanceDto {
    @SerializedName("value")
    @Expose
    var value: Int = 0
}

class LegDto {

    @SerializedName("distance")
    @Expose
    var distance: DistanceDto? = null
}

internal class LocationDto {

    @SerializedName("lat")
    @Expose
    var lat: Double = 0.toDouble()

    @SerializedName("lng")
    @Expose
    var lng: Double = 0.toDouble()

}

internal class PlaceResultDto {

    @SerializedName("geometry")
    @Expose
    var geometry: GeometryDto? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null


    @SerializedName("place_id")
    @Expose
    var placeId: String? = null


    @SerializedName("reference")
    @Expose
    var reference: String? = null

    @SerializedName("vicinity")
    @Expose
    var vicinity: String? = null

}

internal class PlacesResponseDto {


    @SerializedName("results")
    @Expose
    var results: List<PlaceResultDto> = ArrayList()

    @SerializedName("status")
    @Expose
    var status: String? = null
}

internal class PolylineDto {

    @SerializedName("points")
    @Expose
    var points: String? = null
}

internal class RouteDto {

    @SerializedName("overview_polyline")
    @Expose
    var polyline: PolylineDto? = null

    @SerializedName("legs")
    @Expose
    var legs: List<LegDto>? = ArrayList()
}

internal class RouteResponseDto {

    @SerializedName("routes")
    @Expose
    var results: List<RouteDto> = ArrayList()
}