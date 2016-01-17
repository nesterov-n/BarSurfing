package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


internal class RouteDto {

    @SerializedName("overview_polyline")
    @Expose
    var polyline: PolylineDto? = null

    @SerializedName("legs")
    @Expose
    var legs: List<LegDto>? = ArrayList()
}