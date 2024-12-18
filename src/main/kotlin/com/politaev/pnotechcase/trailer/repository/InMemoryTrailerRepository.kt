package com.politaev.pnotechcase.trailer.repository

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.politaev.pnotechcase.trailer.TrailerError
import com.politaev.pnotechcase.trailer.model.Trailer
import org.springframework.stereotype.Repository

@Repository
class InMemoryTrailerRepository : TrailerRepository {

    private val trailersByAssetId = mutableMapOf<Int, Trailer>()
    private val trailersByOwnerId = mutableMapOf<Int, List<Trailer>>().withDefault { emptyList() }

    override fun findTrailerByAssetId(assetId: Int): Either<TrailerError, Trailer> = either {
        ensureNotNull(trailersByAssetId[assetId]) {
            TrailerError.TrailerNotFoundByAssetId(assetId)
        }
    }

    override fun findTrailersByOwnerId(ownerId: Int): List<Trailer> = trailersByOwnerId.getValue(ownerId)

    fun createTrailer(trailer: Trailer) {
        val assetId = trailer.assetId
        val ownerId = trailer.ownerId
        trailersByAssetId.put(assetId, trailer)
        trailersByOwnerId[ownerId] = trailersByOwnerId.getValue(ownerId) + trailer
    }
}