package com.app.electricstations.model

import kotlinx.serialization.SerialName

data class RouteResponse (
    val status: String,
    val message: String,

    @SerialName("route_details")
    val routeDetails: String,

    val distance: Double,

    @SerialName("stations_on_route")
    val stations_on_route: List<NearestStationOnHighway>,

    @SerialName("nearest_station_on_highway")
    val nearestStationOnHighway: NearestStationOnHighway,

    @SerialName("is_reachable")
    val isReachable: Boolean
)
