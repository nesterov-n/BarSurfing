package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LegDto {

    @SerializedName("distance")
    @Expose
    var distance: DistanceDto? = null
}