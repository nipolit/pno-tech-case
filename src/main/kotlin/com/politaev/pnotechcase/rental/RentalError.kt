package com.politaev.pnotechcase.rental

sealed interface RentalError {
    data class RentalNotFound(val customerId:Int, val trailerAssetId: Int) : RentalError
    data class RentalNotUnique(val customerId:Int, val trailerAssetId: Int) : RentalError
}