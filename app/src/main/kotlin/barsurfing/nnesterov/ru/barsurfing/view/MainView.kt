package barsurfing.nnesterov.ru.barsurfing.view

import barsurfing.nnesterov.ru.barsurfing.domain.places.Place


interface MainView {

    fun showCurrentLocation(latitude : Double, longitude : Double);

    fun showPlaces(places: List<Place>);

    fun showLoading();

    fun showError(ex: Throwable?);

    fun clear();
}