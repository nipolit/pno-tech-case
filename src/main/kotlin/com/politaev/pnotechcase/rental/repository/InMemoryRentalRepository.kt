package com.politaev.pnotechcase.rental.repository

import com.politaev.pnotechcase.rental.model.Rental
import org.springframework.stereotype.Repository

@Repository
class InMemoryRentalRepository : RentalRepository {

    private val rentalsByCustomerId = mutableMapOf<Int, Set<Rental>>().withDefault { emptySet() }

    override fun findRentalsByCustomerId(customerId: Int): Set<Rental> = rentalsByCustomerId.getValue(customerId)

    fun createRental(rental: Rental) {
        val customerId = rental.customerId
        rentalsByCustomerId[customerId] = rentalsByCustomerId.getValue(customerId) + rental
    }
}