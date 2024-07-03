package com.app.electricstations.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course (
    @SerialName("course_id")
    val courseID: Long,

    val title: String,
    val description: String,

    @SerialName("teacher_id")
    val teacherID: Long,

    @SerialName("icon_id")
    val iconID: Long,

    val icon: Icon
)