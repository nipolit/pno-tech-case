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

    fun getRentalByCustomerIdAndTrailerAssetId(customerId: Int, trailerAssetId: Int): Either<RentalError, RentalDTO> =
        rentalRepository.findRentalsByCustomerId(customerId)
            .map { toDTOWithTrailer(it) }
            .filter { it.trailer?.assetId == trailerAssetId }
            .run {
                either {
                    when (size) {
                        0 -> raise(RentalError.RentalNotFound(customerId = customerId, trailerAssetId = trailerAssetId))
                        1 -> single()
                        else -> raise(RentalError.RentalNotUnique(customerId = customerId, trailerAssetId = trailerAssetId))
                    }
                }
            }

    private fun toDTOWithTrailer(rental: Rental): RentalDTO = trailerService.getTrailerByAssetId(rental.trailerId)
        .fold(
            { RentalDTO.fromRentalWithoutTrailerDTO(rental) },
            { trailerDTO -> RentalDTO.fromRentalWithTrailerDTO(rental, trailerDTO) }
        )

}