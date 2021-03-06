/*
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <nikolaynesterov@gmail.com> wrote this file.  As long as you retain this notice you
 *  can do whatever you want with this stuff. If we meet some day, and you think
 *  this stuff is worth it, you can buy me a beer in return.   Nikolay Nesterov
 */

package ru.nnesterov.barsurfing.data.location

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import ru.nnesterov.barsurfing.domain.location.LocationProvider
import rx.Observable


class LocationProviderImpl(private val context: Context) : LocationProvider {
    private val reactiveProvider = ReactiveLocationProvider(context)


    override fun getLastKnownLocation(): Observable<Location> {
        return reactiveProvider.lastKnownLocation
    }

    override fun getUpdatableLocation(): Observable<Location> {
        val request = LocationRequest()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)

       return reactiveProvider.getUpdatedLocation(request)
    }
}
