package com.politaev.pnotechcase.trailer.repository

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.politaev.pnotechcase.trailer.error.TrailerError
import com.politaev.pnotechcase.trailer.model.Trailer

class InMemoryTrailerRepository : TrailerRepository {

    private val trailersByAssetId = mutableMapOf<Int, Trailer>()
    private val trailersByOwnerId = mutableMapOf<Int, Set<Trailer>>().withDefault { emptySet() }

    override fun findTrailerById(assetId: Int): Either<TrailerError, Trailer> = either {
        ensureNotNull(trailersByAssetId[assetId]) {
            TrailerError.TrailerNotFound(assetId)
        }
    }

    override fun findTrailersByOwnerId(ownerId: Int): Set<Trailer> = trailersByOwnerId.getValue(ownerId)

    fun createTrailer(trailer: Trailer) {
        val assetId = trailer.assetId
        val ownerId = trailer.ownerId
        trailersByAssetId.put(assetId, trailer)
        trailersByOwnerId[ownerId] = trailersByOwnerId.getValue(ownerId) + trailer
    }
}