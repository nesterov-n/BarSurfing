/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.view

import ru.nnesterov.barsurfing.domain.places.RoutedPlaces


interface MainView {

    fun showCurrentLocation(latitude: Double, longitude: Double)

    fun showPlaces(places: RoutedPlaces)

    fun showLoading()

    fun showError(errorText: String)

    fun showAboutOverlay()

    fun clear()

    fun hideAbout()

    fun hideError()

}