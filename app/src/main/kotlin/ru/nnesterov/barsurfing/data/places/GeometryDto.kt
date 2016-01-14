package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


internal class GeometryDto {

    @SerializedName("location")
    @Expose
    var location: LocationDto? = null

}
