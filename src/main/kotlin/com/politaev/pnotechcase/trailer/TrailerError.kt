package com.politaev.pnotechcase.trailer

sealed interface TrailerError {
    data class TrailerNotFoundByAssetId(val assetId: Int) : TrailerError
    data class TrailerNotFoundByOwnerAndVin(val ownerId: Int, val vin: String) : TrailerError
    data class VinNotUnique(val vin: String) : TrailerError
}