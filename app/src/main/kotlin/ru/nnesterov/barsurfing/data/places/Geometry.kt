package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


internal class Geometry {

    @SerializedName("location")
    @Expose
    var location: Location? = null

}
