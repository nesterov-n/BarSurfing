package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PolylineDto {

    @SerializedName("points")
    @Expose
    var points: String? = null
}
