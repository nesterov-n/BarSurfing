package barsurfing.nnesterov.ru.barsurfing.domain

import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class BarListInteractor(private val placeProvider: PlaceProvider) {

    public fun getNearbyBars(subscriber: Subscriber<List<Place>>): Subscription {
        val filter = PlaceFilter(PlaceType.BAR);
        return placeProvider.getNearByPlaces(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}