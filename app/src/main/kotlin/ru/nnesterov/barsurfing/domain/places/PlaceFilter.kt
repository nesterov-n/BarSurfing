package ru.nnesterov.barsurfing.domain.places


data class PlaceFilter(val placeType: PlaceType,
                       val lattitude: Double,
                       val longitude: Double) {
}