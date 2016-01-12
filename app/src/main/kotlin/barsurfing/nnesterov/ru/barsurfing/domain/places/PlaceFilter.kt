package barsurfing.nnesterov.ru.barsurfing.domain.places


data class PlaceFilter(val placeType: PlaceType,
                       val lattitude: Double,
                       val longitude: Double,
                       val searchRadiusInMeters: Int) {
}