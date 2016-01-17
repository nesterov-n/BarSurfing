package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DistanceDto {
    @SerializedName("value")
    @Expose
    var value: Int = 0
}