package ru.nnesterov.barsurfing.view

import ru.nnesterov.barsurfing.domain.places.RoutedPlaces


interface MainView {

    fun showCurrentLocation(latitude : Double, longitude : Double);

    fun showPlaces(places: RoutedPlaces);

    fun showLoading();

    fun showError(ex: Throwable?);

    fun clear();
}