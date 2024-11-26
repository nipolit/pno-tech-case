package com.politaev.pnotechcase.customer.controller

import com.politaev.pnotechcase.rental.RentalDTO
import com.politaev.pnotechcase.rental.RentalError
import com.politaev.pnotechcase.rental.RentalService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("customers")
class CustomerController(val rentalService: RentalService) {

    @GetMapping("/{customerId}/rentals")
    fun getRentals(
        @PathVariable customerId: Int,
        @RequestParam(required = false) trailerType: String? = null
    ): List<RentalDTO> =
        rentalService.getRentalsByCustomerId(customerId, trailerType)

    @GetMapping("/{customerId}/rentals/{trailerAssetId}")
    fun getSpecificRentalByTrailerAssetId(
        @PathVariable customerId: Int,
        @PathVariable trailerAssetId: Int
    ): RentalDTO =
        rentalService.getRentalByCustomerIdAndTrailerAssetId(customerId = customerId, trailerAssetId = trailerAssetId)
            .fold(
                { rentalError ->
                    when (rentalError) {
                        is RentalError.RentalNotFound -> throw ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No rentals found with customer ID \"${rentalError.customerId}\" and trailer asset ID \"${rentalError.trailerAssetId}\"."
                        )

                        is RentalError.RentalNotUnique -> throw ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Multiple rentals found with customer ID \"${rentalError.customerId}\" and trailer asset ID \"${rentalError.trailerAssetId}\"."
                        )
                    }
                },
                { rental -> rental }
            )
}