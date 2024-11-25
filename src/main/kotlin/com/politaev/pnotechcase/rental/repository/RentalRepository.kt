package com.politaev.pnotechcase.rental.repository

import com.politaev.pnotechcase.rental.model.Rental

interface RentalRepository {
    fun findRentalsByCustomerId(customerId: Int): Set<Rental>
}