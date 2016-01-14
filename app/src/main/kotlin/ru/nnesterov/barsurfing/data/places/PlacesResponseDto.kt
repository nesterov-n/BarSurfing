package ru.nnesterov.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


internal class PlacesResponseDto {


    @SerializedName("results")
    @Expose
    var results: List<PlaceResultDto> = ArrayList()

    @SerializedName("status")
    @Expose
    var status: String? = null
}
