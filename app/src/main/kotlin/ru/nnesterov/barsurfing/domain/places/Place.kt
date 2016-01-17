package ru.nnesterov.barsurfing.domain.places


data class Place(val id: String,
                 val name: String,
                 val address: String,
                 val latitude: Double,
                 val longitude: Double,
                 var visited: Boolean = false)