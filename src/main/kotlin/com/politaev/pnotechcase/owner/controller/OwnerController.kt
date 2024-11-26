package com.politaev.pnotechcase.owner.controller

import com.politaev.pnotechcase.trailer.TrailerDTO
import com.politaev.pnotechcase.trailer.TrailerError
import com.politaev.pnotechcase.trailer.TrailerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("owners")
class OwnerController(val trailerService: TrailerService) {

    @GetMapping("/{ownerId}/trailers")
    fun getTrailers(@PathVariable ownerId: Int, @RequestParam(required = false) vin: String? = null): Set<TrailerDTO> =
        when (vin) {
            null -> trailerService.getTrailersByOwnerId(ownerId)
            else -> trailerService.getTrailerByOwnerIdAndVin(ownerId, vin)
                .fold(
                    { trailerError ->
                        when (trailerError) {
                            is TrailerError.TrailerNotFoundByOwnerAndVin -> emptySet()
                            is TrailerError.VinNotUnique -> throw IllegalStateException("Multiple trailers with VIN  \"$vin\".")
                            else -> throw RuntimeException("Unexpected error when searching for trailer with VIN \"$vin\".")
                        }
                    },
                    { trailerWithVin -> setOf(trailerWithVin) }
                )
        }

}