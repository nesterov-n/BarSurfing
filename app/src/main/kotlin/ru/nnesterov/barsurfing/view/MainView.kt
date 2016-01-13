package ru.nnesterov.barsurfing.view

import ru.nnesterov.barsurfing.domain.places.Place


interface MainView {

    fun showCurrentLocation(latitude : Double, longitude : Double);

    fun showPlaces(places: List<Place>);

    fun showLoading();

    fun showError(ex: Throwable?);

    fun clear();
}