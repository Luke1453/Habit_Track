package com.lake1453.habit_track.model

import java.time.LocalDateTime
import java.util.*

data class Habit(

    var id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var startTime: Date = Date(),
    val repeatable: Boolean = false,
    var repeatPeriod: Int = 0

)



