package com.app.electricstations.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteRequest (
    val city1: String,
    val city2: String,
    val road: String,

    @SerialName("user_current_distance")
    val user_current_distance: Long,

    @SerialName("user_max_distance")
    val user_max_distance: Long
)