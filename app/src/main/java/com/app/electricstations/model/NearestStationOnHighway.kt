package com.app.electricstations.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearestStationOnHighway (
    val identifier: String,
    val icon: String,
    val isBlocked: String,
    val future: Boolean,
    val extent: String,
    val point: String,

    @SerialName("display_type")
    val displayType: String,

    val subtitle: String,
    val title: String,
    val coordinate: Coordinate,
    val description: List<String>,
    val routeRecommendation: ArrayList<String>,
    val footer: ArrayList<String>,
    val lorryParkingFeatureIcons: ArrayList<String>
)
