package com.politaev.pnotechcase.trailer

import arrow.core.Either
import arrow.core.raise.either
import com.politaev.pnotechcase.trailer.repository.TrailerRepository
import org.springframework.stereotype.Service

@Service
class TrailerService(val trailerRepository: TrailerRepository) {

    fun getTrailerByAssetId(assetId: Int): Either<TrailerError, TrailerDTO> =
        trailerRepository.findTrailerByAssetId(assetId)
            .map(TrailerDTO::fromTrailer)

    fun getTrailersByOwnerId(ownerId: Int): List<TrailerDTO> =
        trailerRepository.findTrailersByOwnerId(ownerId)
            .map(TrailerDTO::fromTrailer)

    fun getTrailerByOwnerIdAndVin(ownerId: Int, vin: String): Either<TrailerError, TrailerDTO> {
        val trailersWithOwnerAndVin = trailerRepository.findTrailersByOwnerId(ownerId)
            .filter { it.vin == vin }

        return either {
            when (trailersWithOwnerAndVin.size) {
                0 -> raise(TrailerError.TrailerNotFoundByOwnerAndVin(ownerId, vin))
                1 -> trailersWithOwnerAndVin.single().let(TrailerDTO::fromTrailer)
                else -> raise(TrailerError.VinNotUnique(vin))
            }
        }
    }
}