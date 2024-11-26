package com.politaev.pnotechcase.rental.repository

import com.politaev.pnotechcase.rental.model.Rental
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class InMemoryRentalRepository : RentalRepository {

    private val rentalsByCustomerId = mutableMapOf<Int, List<Rental>>().withDefault { emptyList() }

    override fun findRentalsByCustomerId(customerId: Int): List<Rental> = rentalsByCustomerId.getValue(customerId)
        .filter { LocalDate.now().isBetween(it.startTime, it.endTime) }

    fun createRental(rental: Rental) {
        val customerId = rental.customerId
        rentalsByCustomerId[customerId] = rentalsByCustomerId.getValue(customerId) + rental
    }

    private fun LocalDate.isBetween(startDate: LocalDate, endDate: LocalDate): Boolean = when {
        this.isEqual(startDate) -> true
        this.isEqual(endDate) -> true
        this.isAfter(startDate) && this.isBefore(endDate) -> true
        else -> false
    }

}