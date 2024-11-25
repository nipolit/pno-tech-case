package com.politaev.pnotechcase.trailer

import com.politaev.pnotechcase.trailer.model.Trailer

data class TrailerDTO(
    val assetId: Int,
    val ownerId: Int,
    val model: String,
    val vin: String
) {
    companion object {
        fun fromTrailer(trailer: Trailer) = TrailerDTO(
            assetId = trailer.assetId,
            ownerId = trailer.ownerId,
            model = trailer.model,
            vin = trailer.vin
        )
    }
}
