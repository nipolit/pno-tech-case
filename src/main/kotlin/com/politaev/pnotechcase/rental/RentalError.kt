package com.politaev.pnotechcase.rental

sealed interface RentalError {
    data class RentalNotFoundById(val rentalId: Int) : RentalError
    data class RentalIdNotUnique(val rentalId: Int) : RentalError
}