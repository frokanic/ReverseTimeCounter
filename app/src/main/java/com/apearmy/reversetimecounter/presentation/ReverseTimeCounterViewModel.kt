package com.apearmy.reversetimecounter.presentation


import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReverseTimeCounterViewModel: ViewModel() {


    val startDate = LocalDateTime.parse("2022-12-19T10:15:00")
    val endDate = LocalDateTime.parse("2023-03-10T10:15:00")


    //From now to end Date

    val daysToEndDate = LocalDateTime
        .now()
        .until(endDate, ChronoUnit.DAYS)

    val hoursToEndDate = LocalDateTime
        .now()
        .until(endDate, ChronoUnit.HOURS)

    val minutesToEndDate = LocalDateTime
        .now()
        .until(endDate, ChronoUnit.MINUTES)

    val secondsToEndDate = LocalDateTime
        .now()
        .until(endDate, ChronoUnit.SECONDS)

    //From when I left to end Date

    val daysToEndDateFrom = LocalDateTime
        .from(startDate)
        .until(endDate, ChronoUnit.DAYS)

    val hoursToEndDateFrom = LocalDateTime
        .from(startDate)
        .until(endDate, ChronoUnit.HOURS)

    val minutesToEndDateFrom = LocalDateTime
        .from(startDate)
        .until(endDate, ChronoUnit.MINUTES)

    val secondsToEndDateFrom = LocalDateTime
        .from(startDate)
        .until(endDate, ChronoUnit.SECONDS)


    //They will be used to be able to keep hours, days ect as longs

    val minModulo = (secondsToEndDate % 60).toInt()
    val hourModulo = (secondsToEndDate % 3600).toInt()
    val dayModulo = (secondsToEndDate % 86400).toInt()

}

