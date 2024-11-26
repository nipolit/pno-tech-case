package com.politaev.pnotechcase.rental

import com.politaev.pnotechcase.rental.model.Rental
import com.politaev.pnotechcase.trailer.TrailerDTO
import java.time.LocalDate

class RentalDTO(
    val rentalId: Int,
    val trailerId: Int,
    val customerId: Int,
    val startTime: LocalDate,
    val endTime: LocalDate,
    val trailer: TrailerDTO?
) {
    companion object {
        fun fromRentalWithoutTrailerDTO(rental: Rental) = RentalDTO(
            rentalId = rental.rentalId,
            trailerId = rental.trailerId,
            customerId = rental.customerId,
            startTime = rental.startTime,
            endTime = rental.endTime,
            trailer = null,
        )

        fun fromRentalWithTrailerDTO(rental: Rental, trailer: TrailerDTO) = RentalDTO(
            rentalId = rental.rentalId,
            trailerId = rental.trailerId,
            customerId = rental.customerId,
            startTime = rental.startTime,
            endTime = rental.endTime,
            trailer = trailer,
        )
    }
}