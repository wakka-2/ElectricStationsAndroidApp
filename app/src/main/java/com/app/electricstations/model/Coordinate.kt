package com.app.electricstations.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate (
    val lat: String,
    val long: String
)
