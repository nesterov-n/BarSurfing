package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


internal class RouteResponseDto {

    @SerializedName("routes")
    @Expose
    var results: List<RouteDto> = ArrayList()
}