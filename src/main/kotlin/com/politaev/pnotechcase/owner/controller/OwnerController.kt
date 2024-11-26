package com.politaev.pnotechcase.owner.controller

import com.politaev.pnotechcase.trailer.TrailerDTO
import com.politaev.pnotechcase.trailer.TrailerError
import com.politaev.pnotechcase.trailer.TrailerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("owners")
class OwnerController(val trailerService: TrailerService) {

    @GetMapping("/{ownerId}/trailers")
    fun getTrailers(@PathVariable ownerId: Int, @RequestParam(required = false) vin: String? = null): List<TrailerDTO> =
        when (vin) {
            null -> trailerService.getTrailersByOwnerId(ownerId)
            else -> trailerService.getTrailerByOwnerIdAndVin(ownerId, vin)
                .fold(
                    { trailerError ->
                        when (trailerError) {
                            is TrailerError.TrailerNotFoundByOwnerAndVin -> emptyList()

                            is TrailerError.VinNotUnique -> throw ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Multiple trailers with VIN  \"$vin\"."
                            )

                            else -> throw ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Unexpected error when searching for trailer with VIN \"$vin\"."
                            )
                        }
                    },
                    { trailerWithVin -> listOf(trailerWithVin) }
                )
        }

}