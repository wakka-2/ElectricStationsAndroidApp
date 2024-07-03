package com.app.electricstations.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Icon (
    @SerialName("icon_id")
    val iconID: Long,

    @SerialName("icon_name")
    val iconName: String,

    val link: String? = null
)