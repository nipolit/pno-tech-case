package com.politaev.pnotechcase.rental

import arrow.core.Either
import arrow.core.raise.either
import com.politaev.pnotechcase.rental.model.Rental
import com.politaev.pnotechcase.rental.repository.RentalRepository
import com.politaev.pnotechcase.trailer.TrailerService
import org.springframework.stereotype.Service

@Service
class RentalService(val rentalRepository: RentalRepository, val trailerService: TrailerService) {

    fun getRentalsByCustomerId(customerId: Int, trailerType: String? = null): List<RentalDTO> {
        val rentals = rentalRepository.findRentalsByCustomerId(customerId).map { toDTOWithTrailer(it) }
        return when (trailerType) {
            null -> rentals
            else -> rentals.filter { it.trailer?.model == trailerType }
        }
    }

    fun getRentalByCustomerIdAndRentalId(customerId: Int, rentalId: Int): Either<RentalError, RentalDTO> =
        rentalRepository.findRentalsByCustomerId(customerId)
            .filter { it.rentalId == rentalId }
            .run {
                either {
                    when (size) {
                        0 -> raise(RentalError.RentalNotFoundById(rentalId))
                        1 -> single()
                        else -> raise(RentalError.RentalIdNotUnique(rentalId))
                    }
                }
            }.map { toDTOWithTrailer(it) }

    private fun toDTOWithTrailer(rental: Rental): RentalDTO = trailerService.getTrailerByAssetId(rental.trailerId)
        .fold(
            { RentalDTO.fromRentalWithoutTrailerDTO(rental) },
            { trailerDTO -> RentalDTO.fromRentalWithTrailerDTO(rental, trailerDTO) }
        )

}