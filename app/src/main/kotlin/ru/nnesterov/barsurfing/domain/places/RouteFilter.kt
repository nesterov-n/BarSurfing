/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.domain.places

import com.google.android.gms.maps.model.LatLng


data class RouteFilter(val origin: LatLng,
                       val destination: LatLng,
                       val via: List<LatLng>)