package com.politaev.pnotechcase.trailer.repository

import arrow.core.Either
import com.politaev.pnotechcase.trailer.error.TrailerError
import com.politaev.pnotechcase.trailer.model.Trailer

interface TrailerRepository {
    fun findTrailerById(trailerId: Int): Either<TrailerError, Trailer>
    fun findTrailersByOwnerId(ownerId: Int): Set<Trailer>
}