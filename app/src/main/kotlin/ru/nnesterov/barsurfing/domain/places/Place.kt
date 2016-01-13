package ru.nnesterov.barsurfing.domain.places


data class Place(val id: String,
                 val name: String,
                 val latitude: Double,
                 val longitude: Double) {
}