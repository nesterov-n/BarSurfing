package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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

}
