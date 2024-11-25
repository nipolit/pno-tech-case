package com.politaev.pnotechcase.rental.model

import java.time.LocalDate

data class Rental(
    val rentalId: Int,
    val trailerId: Int,
    val customerId: Int,
    val startTime: LocalDate,
    val endTime: LocalDate
)