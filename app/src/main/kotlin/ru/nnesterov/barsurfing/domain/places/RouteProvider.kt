package ru.nnesterov.barsurfing.domain.places

import rx.Observable


interface RouteProvider {
    fun createRoute(filter: RouteFilter): Observable<Route>
}