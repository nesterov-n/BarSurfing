package barsurfing.nnesterov.ru.barsurfing.data.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


internal class PlacesResponse {


    @SerializedName("results")
    @Expose
    var results: List<Result> = ArrayList()

    @SerializedName("status")
    @Expose
    var status: String? = null
}
