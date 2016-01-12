package barsurfing.nnesterov.ru.barsurfing.view

import barsurfing.nnesterov.ru.barsurfing.domain.Place


interface MainView {

    fun showPlaces(places: List<Place>);

    fun showLoading();

    fun showError(ex: Throwable);

    fun clear();
}